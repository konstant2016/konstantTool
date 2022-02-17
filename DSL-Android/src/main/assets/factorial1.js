
var count = 3;

// 计算阶乘
var factorial = function (n) {
    if (n < 0)
        return;
    if (n === 0)
        return 1;
    return n * factorial(n-1)
};

var add3 = function(a,b){
    count = a + b + 6;
    return count;
}



