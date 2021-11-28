# Самостоятельная работа
## Поведенческий шаблон
Я решил выбрать паттерн Команды (Command)

Пример:
```kotlin
class CommandPattern {

    interface Command {
        fun execute()
    }

    class Receiver {
        fun start() {
            println("Start computer")
        }

        fun stop() {
            println("Stop computer")
        }

        fun reset() {
            println("Reset computer")
        }
    }

    class StartCommand(private val receiver: Receiver): Command {
        override fun execute() {
            receiver.start()
        }
    }
    class StopCommand(private val receiver: Receiver): Command {
        override fun execute() {
            receiver.stop()
        }
    }
    class ResetCommand(private val receiver: Receiver): Command {
        override fun execute() {
            receiver.reset()
        }
    }

    class Invoker(
        private var start: StartCommand,
        private var stop: StopCommand,
        private var reset: ResetCommand
    ) {
        fun startComputer() {
            start.execute()
        }
        fun stopComputer() {
            stop.execute()
        }
        fun resetComputer() {
            reset.execute()
        }
    }
}


```

Использование:
```kotlin
fun main() {
    val command = Receiver()
    val invoker = Invoker(
        StartCommand(command),
        StopCommand(command),
        ResetCommand(command)
    )
    invoker.startComputer()
    invoker.stopComputer()
    invoker.resetComputer()
}
```

Выходные данные:
```
Start computer
Stop computer
Reset computer
```

***

## Пораждающий шаблон
В качестве пораждающего паттерна я решил взять Фабричный метод (Fabric Method)

Пример:
```kotlin
class FactoryMethod {
    interface Fruits {
        fun mutate()
    }

    class Orange : Fruits {
        override fun mutate() {
            println("Это апельсин!")
        }
    }

    class Apple : Fruits {
        override fun mutate() {
            println("Это яблоко!")
        }
    }

    class Mango : Fruits {
        override fun mutate() {
            println("А это манго!")
        }
    }

    enum class FruitsType {
        Orange, Apple, Mango
    }

    class FruitsFactory {
        fun showFruits(type: FruitsType): Fruits? {
            return when (type) {
                FruitsType.Orange -> Orange()
                FruitsType.Apple -> Apple()
                FruitsType.Mango -> Mango()
                else -> null
            }
        }
    }
}
```

Использование:
```kotlin
fun main() {
    val factory = FactoryMethod.FruitsFactory()
    val orange = factory.showFruits(FactoryMethod.FruitsType.Orange)
    val apple = factory.showFruits(FactoryMethod.FruitsType.Apple)
    val mango = factory.showFruits(FactoryMethod.FruitsType.Mango)
    orange?.mutate()
    apple?.mutate()
    mango?.mutate()
}
```

Выходные данные:
```
Это апельсин!
Это яблоко!
А это манго!
```

***

## Структурный шаблон
Из структурных я решил выбрать Фасадный метод (Facade)

Пример:
```kotlin
class Facade {
    enum class Fruit {
        Pineapple,
        Orange,
        Mango;
    }

    interface Juice {
        fun mix(element: String, baseFruit: Fruit)
    }

    class PineappleJuice : Juice {
        override fun mix(element: String, baseFruit: Fruit) {
            println("Add $element to ${baseFruit.name} juice concentrate")
        }
    }

    class OrangeJuice : Juice {
        override fun mix(element: String, baseFruit: Fruit) {
            println("Add $element to ${baseFruit.name} juice concentrate")
        }
    }

    class MangoJuice : Juice {
        override fun mix(element: String, baseFruit: Fruit) {
            println("Add $element to ${baseFruit.name} juice concentrate")
        }
    }

    class JuiceManager {
        private val pineappleJuice = PineappleJuice()
        private val orangeJuice = OrangeJuice()
        private val mangoJuice = MangoJuice()

        fun mix(element: String, finJuice: Fruit) {
            when (finJuice) {
                Fruit.Pineapple -> pineappleJuice.mix(element, finJuice)
                Fruit.Orange -> orangeJuice.mix(element, finJuice)
                Fruit.Mango -> mangoJuice.mix(element, finJuice)
            }
        }
    }
}
```

Использование:
```kotlin
fun main() {
    val juiceManager = Facade.JuiceManager()
    juiceManager.mix("water", Facade.Fruit.Pineapple)
    juiceManager.mix("apple juice and water", Facade.Fruit.Orange)
    juiceManager.mix("orange juice and water", Facade.Fruit.Mango)
}
```

Выходные данные:
```
Add water to Pineapple juice concentrate
Add apple juice and water to Orange juice concentrate
Add orange juice and water to Mango juice concentrate
```
