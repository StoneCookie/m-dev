# Самостоятельная работа
## Поведенческий шаблон
Я решил выбрать паттерн команды (Command)
Изначально у нас есть получатель Bulb, в котором есть реализация каждого действия, которое может быть выполнено:
```java
// Получатель
class Bulb
{
    public function turnOn()
    {
        echo "Лампочка загорелась";
    }

    public function turnOff()
    {
        echo "Темнота!";
    }
}
```

Затем у нас есть интерфейс Command, который каждая команда должна реализовывать, и затем у нас будет набор команд:
```java
interface Command
{
    public function execute();
    public function undo();
    public function redo();
}

// Команда
class TurnOn implements Command
{
    protected $bulb;

    public function __construct(Bulb $bulb)
    {
        $this->bulb = $bulb;
    }

    public function execute()
    {
        $this->bulb->turnOn();
    }

    public function undo()
    {
        $this->bulb->turnOff();
    }

    public function redo()
    {
        $this->execute();
    }
}

class TurnOff implements Command
{
    protected $bulb;

    public function __construct(Bulb $bulb)
    {
        $this->bulb = $bulb;
    }

    public function execute()
    {
        $this->bulb->turnOff();
    }

    public function undo()
    {
        $this->bulb->turnOn();
    }

    public function redo()
    {
        $this->execute();
    }
}
```

Затем у нас есть Invoker, с которым клиент будет взаимодействовать для обработки любых команд:
```java
// Invoker
class RemoteControl
{
    public function submit(Command $command)
    {
        $command->execute();
    }
}
```

Наконец, мы можем увидеть, как использовать нашего клиента:
```java
$bulb = new Bulb();

$turnOn = new TurnOn($bulb);
$turnOff = new TurnOff($bulb);

$remote = new RemoteControl();
$remote->submit($turnOn); // Лампочка загорелась!
$remote->submit($turnOff); // Темнота!
```
Шаблон команда может быть использован для реализации системы, основанной на транзакциях, где вы сохраняете историю команд, как только их выполняете.
