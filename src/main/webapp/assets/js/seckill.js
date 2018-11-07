;
var seckill = {
    url: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        kill: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/kill';
        }
    },
    detail: {
        init: function (params) {
            var phone = $.cookie('userPhone');
            var seckillId = params.seckillId;
            var startTime = params.startTime;
            var endTime = params.endTime;

            if (!seckill.validPhone(phone)) {
                var modal = $('#loginModal');
                modal.modal({show: true, keyboard: false, backdrop: 'static'});
            }

            $('#btn-submit').on('click', function (e) {
                var v = $('#userPhone').val();
                if (seckill.validPhone(v)) {
                    $.cookie('userPhone', v, {expires: 7, path: '/seckill'});
                    window.location.reload();
                } else {
                    $('#message-box').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                }
            });

            $.get(seckill.url.now(), {}, function (d) {
                if (d && d.success) {
                    seckill.countDown(seckillId, d.data, startTime, endTime);
                }
            });
        }
    },
    countDown: function (seckillId, nowTime, startTime, endTime) {
        var box = $('#seckill-box');
        if (nowTime > endTime) {
            box.html('秒杀结束');
        } else if (nowTime < startTime) {
            box.html('秒杀未开始');
            // 倒计时
            var dt = new Date(startTime + 1000);

            box.countdown(dt, function (event) {
                $(this).html(event.strftime("秒杀倒计时：%D天 %H时 %M分 %S秒"));
            }).on('finish.countdown', function () {
                // 获取地址，显示秒杀按钮
                seckill.handleKill(seckillId, box);
            });
        } else {
            // 秒杀开始
            seckill.handleKill(seckillId, box);
        }
    },
    handleKill: function (seckillId, el) {
        el.hide().html('<button id="btn-kill" class="btn btn-info">开始秒杀</button>').show();
        $.post(seckill.url.exposer(seckillId), {seckillId: seckillId}, function (d) {
            if (d && d.success) {
                var exposer = d.data;
                if (exposer.exposed) {
                    // 开启秒杀
                    var killUrl = seckill.url.kill(seckillId, exposer.md5);
                    $('#btn-kill').one('click', function () {
                        var e = $(this);
                        e.addClass('disabled');
                        // 发送秒杀请求
                        $.post(killUrl, {}, function (d) {
                            if (d && d.success) {
                                el.html('<span class="label label-info">' + d.data.stateInfo + '</span>');
                            } else {
                                el.html('<span class="label label-warning">' + d.data.stateInfo + '</span>');
                            }
                        });
                    });
                    $('#btn-kill').show();
                } else {
                    // 未开启秒杀（客户端时间与服务器时间或存在差异）
                    var now = exposer.now, start = exposer.start, end = exposer.end;
                    seckill.countDown(seckillId, now, start, end);
                }
            }
        });
    },
    validPhone: function (phone) {
        return (phone && phone.length == 11 && !isNaN(phone));
    }

};