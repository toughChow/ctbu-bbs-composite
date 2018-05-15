$(function () {
    $('.img-circle').on('click',function () {
        $('.dropdown-menu').toggle();
    })
    var btnDoms = document.getElementsByClassName("btna");
    var boxDom = document.getElementById("box");
    var contentUntiDoms = document.getElementsByClassName("content-unit");
    var leftDom = document.getElementById("left_ear");
    var rightDom = document.getElementById("right_ear");
    var last = 0, next = 0, _index = 1, final = contentUntiDoms.length - 1;
    //向左
    leftDom.addEventListener("click", leftGo, false);

    function leftGo () {
        toChock();
        next = --next < 0 ? 4 : next;
        contentUntiDoms[next].style.transition = "none";
        contentUntiDoms[next].style.left = -1 * 296 + "px";
        setTimeout(function () {
            contentUntiDoms[next].style.transition = "all 1.5s ease";
            contentUntiDoms[next].style.left = 0;
            contentUntiDoms[last].style.left = 296 + "px";
            btnDoms[last].className = btnDoms[last].className.replace(" active", "");
            btnDoms[next].className += " active";
            last = next;
        }, 2000);
        setTimeout(function () {
            contentUntiDoms[0].style.left = 296;
        }, 2000);
    }

    //向右
    rightDom.addEventListener("click", rightGo, false);

    function rightGo () {
        toChock();
        toGo();
    }

    // 点击按钮节流做法
    boxDom.addEventListener("click", dian, false);

    function dian (e) {
        e = e || window.event;
        if (e.target.className.indexOf("btn") !== -1) {
            toChock();
            boxDom.removeEventListener("click", dian, false);
            setTimeout(function () {
                boxDom.addEventListener("click", dian, false);
            }, 2000);
            next = e.target.getAttribute("data-index");
            contentUntiDoms[next].style.transition = "all 1.5s ease";
            contentUntiDoms[last].style.left = -1 * 296 + "px";
            contentUntiDoms[next].style.left = 0;
            setTimeout(function () {
                contentUntiDoms[last].style.transition = "none";
                contentUntiDoms[last].style.left = 296 + "px";
                last = next;
            }, 1500);
            btnDoms[last].className = btnDoms[last].className.replace(" active", "");
            btnDoms[next].className += " active";
        }
    }

    //自动
    var timer = setInterval(toGo, 2000);

    //鼠标移入
    boxDom.onmouseenter = function () {
        clearInterval(timer);
    };
    //鼠标移出
    boxDom.onmouseleave = function () {
        timer = setInterval(toGo, 2000);
    };

    //移动
    function toGo () {
        next = ++next > 4 ? 0 : next;
        contentUntiDoms[next].style.transition = "all 1.5s ease";
        contentUntiDoms[last].style.left = -1 * 296 + "px";
        contentUntiDoms[next].style.left = 0;
        setTimeout(function () {
            contentUntiDoms[last].style.transition = "none";
            contentUntiDoms[last].style.left = 296 + "px";
            last = next;
        }, 2000);
        btnDoms[last].className = btnDoms[last].className.replace(" active", "");
        btnDoms[next].className += " active";
    }

    //左右耳朵节流
    function toChock () {
        rightDom.removeEventListener("click", rightGo, false);
        leftDom.removeEventListener("click", leftGo, false);
        setTimeout(function () {
            rightDom.addEventListener("click", rightGo, false);
            leftDom.addEventListener("click", leftGo, false);
        }, 1600);
    }
})