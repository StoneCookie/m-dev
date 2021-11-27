# Самостоятельная работа
## Поведенческий шаблон
Я решил выбрать паттерн Команды (Command)

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

## Пораждающий шаблон
В качестве пораждающего паттерна я решил взять Фабричный метод (Fabric Method)

Изначально у нас есть интерфейс Interviewer и несколько реализаций для него:
```java
interface Interviewer
{
    public function askQuestions();
}

class Developer implements Interviewer
{
    public function askQuestions()
    {
        echo 'Спрашивает про шаблоны проектирования!';
    }
}

class CommunityExecutive implements Interviewer
{
    public function askQuestions()
    {
        echo 'Спрашивает о работе с сообществом';
    }
}
```

Теперь создадим нашего HiringManager:
```java
abstract class HiringManager
{

    // Фабричный метод
    abstract public function makeInterviewer(): Interviewer;

    public function takeInterview()
    {
        $interviewer = $this->makeInterviewer();
        $interviewer->askQuestions();
    }
}
```

И теперь любой дочерний класс может расширять его и предоставлять необходимого интервьюера:
```java
class DevelopmentManager extends HiringManager
{
    public function makeInterviewer(): Interviewer
    {
        return new Developer();
    }
}

class MarketingManager extends HiringManager
{
    public function makeInterviewer(): Interviewer
    {
        return new CommunityExecutive();
    }
}
```

Пример использования:
```java
$devManager = new DevelopmentManager();
$devManager->takeInterview(); // Вывод: Спрашивает о шаблонах проектирования!

$marketingManager = new MarketingManager();
$marketingManager->takeInterview(); // Вывод: Спрашивает о работе с сообщест
```
Полезен, когда есть некоторая общая обработка в классе, но необходимый подкласс динамически определяется во время выполнения.

## Структурный шаблон
Из структурных я решил выбрать Компоновщик (Composite)

Возьмем наш пример с рабочими. У нас есть Employee разных типов:
```java
interface Assignee {
  public function canHandleTask($task): bool;
  public function takeTask($task);
}

class Employee implements Assignee {
  // реализуем методы интерфейса
}

class Team implements Assignee {
  /** @var Assignee[] */
  private $assignees;

  // вспомогательные методы для управления композитом:
  public function add($assignee);
  public function remove($assignee);

  // метода интерфейса Employee

  public function canHandleTask($task): bool {
    foreach ($this->assignees as $assignee) if ($assignee->canHandleTask($task)) return true;
    return false;
  }
  public function takeTask($task) {
    // может быть разная имплементация - допустим, некоторые задания требуют нескольких человек из команды одновременно
    // в простейшем случае берем первого незанятого работника среди this->assignees
    $assignee = ...;
    $assignee->takeTask($task);
  }
}
```

Теперь у нас есть TaskManager:
```java
class TaskManager {
  private $assignees;
  public function performTask($task) {
    foreach ($this->assignees as $assignee) {
       if ($assignee->canHandleTask($task)) {
         $assignee->takeTask($task);
         return;
       }
    }

    throw new Exception('Cannot handle the task - please hire more people');
  }
}
```

Способ применения:
```java
$employee1 = new Employee();
$employee2 = new Employee();
$employee3 = new Employee();
$employee4 = new Employee();
$team1 = new Team([$employee3, $employee4);

// ВНИМАНИЕ: передаем команду в taskManager как единый композит.
// Сам taskManager не знает, что это команда и работает с ней без модификации своей логики.
$taskManager = new TaskManager([$employee1, $employee2, $team1]);
$taskManager->preformTask($task);
```
