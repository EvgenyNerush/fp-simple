import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

fun main() {

    // Попробуем написать алгоритм rejection sampling в функциональном стиле.
    // Этот алгоритм позволяет сгенерировать набор точек с наперёд заданной
    // функцией распределения (target distribution).

    println("---- variant 1 ----")

    val n: Int = 5
    val s = DoubleArray(n)

    // [1, 2, 3].map(f) = [f(1), f(2), f(3)]
    // В данном случае мы заменяем все элементы массива на случайные числа,
    // равномерно распределённые от 0 до 1.
    val xs = s.map( {_ -> Random.nextDouble()} )
    println("xs = $xs")

    // можно обойтись и без лишних скобок
    val ys = s.map {_ -> Random.nextDouble()}
    println("ys = $ys")

    // zip из двух массивов делает один массив из пар: zip( [1, 2], [3, 4] ) =
    // [(1, 3), (2. 4)]
    val zs = xs.zip(ys)
    println("zs = $zs")

    // Тип "со знаком вопроса" - nullable - означает, что переменные этого типа могут
    // как содержать значение, так и не содержать ничего (null reference).
    // `readLine()` возвращает `String?`, поскольку чтение может произойти с ошибкой.
    println("Enter an integer (or not):")
    val st: String? = readLine()
    // Если чтение прошло без ошибки, пытаемся сконвертировать строку в Int
    val i: Int? = st?.toIntOrNull()
    // Если конверсия прошла с ошибкой, возвращаем -1; `?:` - Elvis operator
    val j: Int  = i?.toInt() ?: -1
    // можно напечатать `j`, но мы сделаем всё то же самое в одном выражении;
    // стоит отметить, что как при ошибке чтения, так и при ошибки конверсии
    // напечатается значение -1
    println("You have entered ${st?.toIntOrNull() ?: -1}")

    // Отбросим часть точек алгоритмом rejection sampling,
    // target distribution = x,
    // обратите внимание на аргумент лямбды - используется pattern matching
    val ts = zs.map {(x, y) -> if (y < x) x else null}
    // filter оставляет только те значения, для которых передаваемая функция
    // возвращает true
    val rs = ts.filter {x -> x != null} // or just {it != null}
    println("rs = ${rs}")

    println("---- variant 2 ----")

    // Мы пользовались множеством промежуточных переменных (типы которых были
    // всегда разными), однако нашу программу можно написать очень ясно и коротко,
    // используя оператор точки (доступ к функции-члену) вместо композиции функций
    val m: Int = 1000
    val qs = DoubleArray(m)
    val ws = qs
        .map {_ -> Pair(Random.nextDouble(), Random.nextDouble())}
        .filter {(x, y) -> y < x}
        .map {(x, _) -> x}

    // Случайные числа с нужным распределением получены! Вычислим для них
    // среднее и дисперсию. Стоит отметить, что ws.size != m (!)
    val mean: Double = ws.foldRight(0.0, Double::plus) / ws.size
    val dispersion: Double = ws
        .map {x -> (x - mean).pow(2)}
        .foldRight(0.0, Double::plus) / ws.size

    println("mu = ${mean}, sigma = ${sqrt(dispersion)}")
}