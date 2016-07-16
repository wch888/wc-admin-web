/**
 * Created by admin on 2015-08-18.
 */
(function ($) {

    function Area(options) {
        $.fn.extend(this, {
            "parentCode": 0,
            "level": 4,
            "url": "/city/getByParentId",
            "provinceCode": 2298,
            "type": "get",
            "cityCode": 2299,
            "countyCode": 2301,
            "showSelect": false,
            provinceName: "province",
            cityName: "city",
            countyName: "area"
        }, options);
        this.init();
    }

    Area.prototype = {
        init: function () {
            var s1 = '<select class="province"  name="' + this.provinceName + '"></select>';
            var s2 = '<select class="city" name="' + this.cityName + '"></select>';
            var s3 = '<select class="county" name="' + this.countyName + '"></select>';
            this.input.append(s1);
            this.input.append(s2);
            this.input.append(s3);
            this.province = this.input.find(".province");
            this.city = this.input.find(".city");
            this.county = this.input.find(".county");
            this.initProvince();
            this.initCity();
            this.initCounty();
            this.changeProvince();
            var $this = this;
            this.city.change(function () {
                $this.changeCity();
            });
        },
        initProvince: function () {
            var $this = this;
            $.ajax({
                type: "get",
                url: $this.url,
                data: {parentCode: $this.parentCode}
            }).done(function (msg) {
                var len = msg.length;
                var html = [];
                for (var i = 0; i < len; i++) {
                    if (msg[i].id == $this.provinceCode) {
                        html[i] = '<option value="' + msg[i].id + '" selected>' + msg[i].cityName + '</option>';
                    } else {
                        html[i] = '<option value="' + msg[i].id + '">' + msg[i].cityName + '</option>';
                    }
                }
                $this.province.html(html.join("")).css('zoom', '1');
            });
        },
        initCity: function () {
            var $this = this;
            $.ajax({
                type: "get",
                url: $this.url,
                data: {parentCode: $this.provinceCode}
            }).done(function (msg) {
                if ($this.showSelect) {
                    var y = document.createElement("option");
                    y.value = 0;
                    y.text = "请选择";
                    $this.city[0].add(y);
                }
                $.each(msg, function (i, n) {
                    var y = document.createElement("option");
                    y.value = n.id;
                    y.text = n.cityName;
                    $this.city[0].add(y);
                });
                $this.city.val($this.cityCode);
            });
        },
        initCounty: function () {
            var $this = this;
            $.ajax({
                type: "get",
                url: $this.url,
                data: {parentCode: $this.cityCode}
            }).done(function (msg) {
                var len = msg.length;
                var html = [];
                if ($this.showSelect) {
                    html[0] = '<option value="0">请选择</option>';
                }

                for (var i = 0; i < len; i++) {
                    if (msg[i].id == $this.countyCode) {
                        html[i] = '<option value="' + msg[i].id + '" selected>' + msg[i].cityName + '</option>';
                    } else {
                        html[i] = '<option value="' + msg[i].id + '">' + msg[i].cityName + '</option>';
                    }
                }
                $this.county.html(html.join("")).css('zoom', '1');
                $this.county.val($this.countyCode);
            });
        },
        changeProvince: function () {
            var $this = this;
            this.province.change(function () {
                var parentCode = $this.province.val();
                if (parentCode * 1) {
                    $.get($this.url, {parentCode: parentCode}, function (msg) {
                        $this.city[0].innerHTML = "";
                        $.each(msg, function (i, n) {
                            var y = document.createElement("option");
                            y.value = n.id;
                            y.text = n.cityName;
                            $this.city[0].add(y);
                        });
                        if (msg.length) {
                            $this.changeCity(msg[0].id);
                        }
                    });
                } else {
                    if ($this.showSelect) {
                        var y = document.createElement("option");
                        y.value = 0;
                        y.text = "请选择";
                        $this.city[0].add(y);
                    }
                }
            });
        },
        changeCity: function (parent) {
            var $this = this;

            var parentCode = $this.city.val();

            parentCode = !!parent ? parent : parentCode;

            if (parentCode * 1) {
                $.get($this.url, {parentCode: parentCode}, function (msg) {
                    $this.county[0].innerHTML = "";
                    $.each(msg, function (i, n) {
                        var y = document.createElement("option");
                        y.value = n.id;
                        y.text = n.cityName;
                        $this.county[0].add(y);
                    });
                });
            } else {
                if ($this.showSelect) {
                    var y = document.createElement("option");
                    y.value = 0;
                    y.text = "请选择";
                    $this.county[0].add(y);
                }
            }
        }
    }

    $.fn.extend({

        //提交到后台
        areaPicker: function (options) {
            var $this = $(this);
            options.input = $this;
            var area = new Area(options);

            return this;
        }
    });

})(jQuery);