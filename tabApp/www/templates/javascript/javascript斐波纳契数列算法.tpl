一般斐波纳契数列采用递归或是数组缓存的方式。
fibonacci 数列定义，查看百度百科的解释>>
  n = 1,2 时，fib(n) = 1 
  n > 2 时，fib(n) = fib(n-2) + fib(n-1)

1、递归
function Fib(n) { 
  return n < 2 ? n : (Fib(n - 1) + Fib(n - 2)); 
}

2、数组缓存

function IterMemoFib(n) { 
  var cache = [1, 1]; 
  if (n >= cache.length) { 
    for (var i = cache.length; i < n ; i++ ) { 
       cache[i] = cache[i - 2] + cache[i - 1]; 
     } 
   } 
        return cache[n - 1]; 
 } 
3、直接使用加法
function fib(n) { 
    if (n < 2) { 
        return 1; 
    } 
    var a = 1, b = 1; 
    for (var i = 2; i < n - 1 ;i++ ) { 
        b = a + b; 
        a = b - a; 
    } 
    return a + b; 
}
对比：
如果只使用一次运算，第三种方法速度最快；
如果多次使用，第二种方法明显优于其它两种；
在n较大的情况下不推荐使用第一种；n为10*10000的时候递归就已经报内存溢出了

下面是在IE8下测试的结果(n为100W)：



如果只需要计算一次，第三种方法应该是最优的，而且当n越大的时候，数组占有的内存空间也将越大