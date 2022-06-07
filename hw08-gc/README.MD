Замеры 

Тестовый компьютер : 
Процессор: Intel(R) Core(TM) i7-6950X CPU @ 3.00GHz   3.00 GHz
ОП:   128 ГБ     


spend msec:13927, sec:13 - по дефолту( без настроек)
spend msec:13924, sec:13 -  256m
spend msec:14102, sec:14 -  512m
spend msec:13508, sec:13 -  1024m
spend msec:13249, sec:13 -  1516m
spend msec:13719, sec:13 -  1616m
spend msec:14039, sec:14 -  2048m

Recommended heap space - 1516m

Оптимизация 6_600_000 -> 5_700_000

spend msec:13834, sec:13 - по дефолту( без настроек) 6_600_000
spend msec:14083, sec:14 -  256m                     6_600_000
spend msec:13582, sec:13 -  256m                     6_000_000
spend msec:13508, sec:13 -  256m                     5_750_000
spend msec:13259, sec:13 -  256m                     5_700_000
spend msec:13254, sec:13 -  256m                     5_700_000

Result optimal - 256m                     5_700_000

Оптимизация Integer - > int

spend msec:3378, sec:3 - по дефолту( без настроек) 6_600_000
spend msec:3430, sec:3 - 256m                     6_600_000
spend msec:3265, sec:3 - 256m                     6_000_000
spend msec:3225, sec:3 - 256m                     5_750_000
spend msec:3259, sec:3 - 256m                     5_700_000

Result optimal - int 256m  5_750_000
