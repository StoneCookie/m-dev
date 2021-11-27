# Самостоятельная работа
## Поведенческий шаблон
Я решил выбрать паттерн Команды (Command)

Пример:
```kotlin
interface OrderCommand {
    fun execute()
}

class OrderAddCommand(val id: Long) : OrderCommand {
    override fun execute() = println("Adding order with id: $id")
}

class OrderPayCommand(val id: Long) : OrderCommand {
    override fun execute() = println("Paying for order with id: $id")
}

class CommandProcessor {

    private val queue = ArrayList<OrderCommand>()

    fun addToQueue(orderCommand: OrderCommand): CommandProcessor =
        apply {
            queue.add(orderCommand)
        }

    fun processCommands(): CommandProcessor =
        apply {
            queue.forEach { it.execute() }
            queue.clear()
        }
}
```

Использование:
```kotlin
CommandProcessor()
    .addToQueue(OrderAddCommand(1L))
    .addToQueue(OrderAddCommand(2L))
    .addToQueue(OrderPayCommand(2L))
    .addToQueue(OrderPayCommand(1L))
    .processCommands()
```

Выходные данные:
```
Adding order with id: 1
Adding order with id: 2
Paying for order with id: 2
Paying for order with id: 1
```

***

## Пораждающий шаблон
В качестве пораждающего паттерна я решил взять Фабричный метод (Fabric Method)

Пример:
```kotlin
sealed class Country {
    object USA : Country()
}

object Spain : Country()
class Greece(val someProperty: String) : Country()
data class Canada(val someProperty: String) : Country()

class Currency(
    val code: String
)

object CurrencyFactory {

    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Greece -> Currency("EUR")
            is Spain -> Currency("EUR")
            is Country.USA -> Currency("USD")
            is Canada -> Currency("CAD")
        }
}
```

Использование:
```kotlin
val greeceCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
println("Greece currency: $greeceCurrency")

val usaCurrency = CurrencyFactory.currencyForCountry(Country.USA).code
println("USA currency: $usaCurrency")

assertThat(greeceCurrency).isEqualTo("EUR")
assertThat(usaCurrency).isEqualTo("USD")
```

Выходные данные:
```
Greece currency: EUR
US currency: USD
UK currency: No Currency Code Available
```

***

## Структурный шаблон
Из структурных я решил выбрать Компоновщик (Composite)

Пример:
```kotlin
open class Equipment(private var price: Int, private var name: String) {
    open fun getPrice(): Int = price
}

/*
[composite]
*/

open class Composite(name: String) : Equipment(0, name) {
    val equipments = ArrayList<Equipment>()

    fun add(equipment: Equipment) {
        this.equipments.add(equipment)
    }

    override fun getPrice(): Int {
        return equipments.map { it.getPrice() }.sum()
    }
}

/*
 leafs
*/

class Cabbinet : Composite("cabbinet")
class FloppyDisk : Equipment(70, "Floppy Disk")
class HardDrive : Equipment(250, "Hard Drive")
class Memory : Equipment(280, "Memory")
```

Использование:
```kotlin
var cabbinet = Cabbinet()
cabbinet.add(FloppyDisk())
cabbinet.add(HardDrive())
cabbinet.add(Memory())
println(cabbinet.getPrice())
```

Выходные данные:
```
600
```
