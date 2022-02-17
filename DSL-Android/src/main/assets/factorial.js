
var count = 3;

// 计算阶乘
var factorial = function (n) {
    if (n < 0)
        return;
    if (n === 0)
        return 1;
    return n * factorial(n-1)
};

var add = function(a,b){
    count = a + b;
    return count;
}

var add2 = function(){
//    return count * 10;
    return addOnJavaScript(3,5);
}



