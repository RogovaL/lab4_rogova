# Лабораторна робота №4
Лабораторна робота виконана за допомогою Jetpack Compose.

## Зміст проекту
В даній лабораторній роботі представлено Android-додаток, який демонструє:
1. Адаптивний макет, що змінюється залежно від орієнтації пристрою
2. Використання ViewPager з фрагментами та навігацією
3. Навігацію між екранами за допомогою Jetpack Navigation

## Структура проекту

### MainActivity.kt
В `MainActivity` реалізовано вхідну точку додатку та систему навігації між екранами за допомогою Jetpack Navigation. Цей файл є основою всього додатку і відповідає за ініціалізацію його інтерфейсу.

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}
```

**Опис блоку `onCreate()`:**
Цей блок коду є точкою входу для нашого додатку. Тут відбувається:
1. Перевизначення стандартного методу `onCreate()` активності
2. Виклик батьківського методу `super.onCreate(savedInstanceState)` для збереження стану
3. Встановлення контенту через Jetpack Compose функцію `setContent {}`
4. Застосування `MaterialTheme` для використання стилів Material Design у всьому додатку
5. Створення `Surface` як кореневого контейнера, який займає весь екран
6. Встановлення кольору фону з палітри Material Design
7. Виклик `MainApp()` - головної композабельної функції додатку

Функція `MainApp()` налаштовує навігацію між головним меню та двома екранами завдань:

```kotlin
@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenu(
                onNavigateToTask1 = { navController.navigate("task1") },
                onNavigateToTask2 = { navController.navigate("task2") }
            )
        }
        composable("task1") {
            AdaptiveLayoutScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable("task2") {
            FragmentPagerScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}
```

**Опис блоку `MainApp()`:**
Ця композабельна функція відповідає за налаштування системи навігації додатку:
1. Створює та запам'ятовує `NavController` - основний компонент для керування навігацією
2. Створює `NavHost` - контейнер, що містить всі маршрути та екрани навігації
3. Визначає "main_menu" як початковий пункт призначення
4. Реєструє три основні маршрути навігації:
   - "main_menu" - головне меню з кнопками для навігації до завдань
   - "task1" - екран з адаптивним макетом
   - "task2" - екран з ViewPager та фрагментами
5. Передає функції навігації у відповідні компоненти:
   - В `MainMenu` передає функції для переходу до завдань
   - В екрани завдань передає функцію для повернення на головний екран (`popBackStack()`)
6. Використовує лямбда-функції для створення замикань, що зберігають контекст навігації

Функція `MainMenu()` створює інтерфейс головного екрану з двома кнопками для навігації:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(
    onNavigateToTask1: () -> Unit,
    onNavigateToTask2: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fragments App") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToTask1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Завдання 1: Адаптивний макет")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToTask2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Завдання 2: ViewPager с навігацією")
            }
        }
    }
}
```

**Опис блоку `MainMenu()`:**
Ця композабельна функція відповідає за створення головного меню додатку:
1. Анотація `@OptIn(ExperimentalMaterial3Api::class)` дозволяє використовувати експериментальні API Material 3
2. Функція приймає два параметри типу `() -> Unit` - лямбда-функції для навігації
3. Використовує `Scaffold` для створення типової структури екрану з верхньою панеллю
4. Створює `TopAppBar` з заголовком "Fragments App"
5. У основному вмісті:
   - Отримує `paddingValues` від Scaffold для коректного розміщення контенту
   - Створює вертикальну `Column`, що заповнює весь простір екрану
   - Центрує вміст вертикально та горизонтально
   - Додає додатковий відступ 16dp з усіх сторін
6. Створює першу кнопку:
   - При натисканні викликає функцію `onNavigateToTask1`
   - Кнопка займає всю ширину екрану
   - Має вертикальні відступи 8dp
   - Містить текст "Завдання 1: Адаптивний макет"
7. Додає вертикальний проміжок висотою 16dp через `Spacer`
8. Створює другу кнопку:
   - При натисканні викликає функцію `onNavigateToTask2`
   - Аналогічні параметри розміру та відступів як у першої кнопки
   - Містить текст "Завдання 2: ViewPager с навігацією"

### AdaptiveLayoutScreen.kt
Цей файл містить реалізацію адаптивного макету, який змінює своє відображення залежно від орієнтації пристрою. Це демонструє можливість створення інтерфейсів, які оптимально використовують простір екрану як у портретній, так і в ландшафтній орієнтації.

Основна структура:
```kotlin
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveLayoutScreen(
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Завдання 1: Адаптивний макет") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AdaptiveLayout(Modifier.padding(paddingValues))
    }
}
```

**Опис блоку `AdaptiveLayoutScreen()`:**
Ця композабельна функція відповідає за створення екрану з адаптивним макетом:
1. Анотація `@OptIn(ExperimentalMaterial3Api::class)` дозволяє використовувати експериментальні API Material 3
2. Функція приймає параметр `onBackPressed: () -> Unit` - лямбда-функцію для повернення назад
3. Використовує `Scaffold` для створення стандартної структури екрану
4. Налаштовує `TopAppBar` з:
   - Заголовком "Завдання 1: Адаптивний макет"
   - Кнопкою "назад" (іконка стрілки) в лівій частині шапки
   - Кнопка повернення при натисканні викликає передану функцію `onBackPressed`
5. У основному вмісті:
   - Отримує `paddingValues` від Scaffold для коректного розміщення контенту
   - Викликає функцію `AdaptiveLayout` для відображення адаптивного інтерфейсу
   - Передає модифікатор `Modifier.padding(paddingValues)` для застосування відступів від Scaffold

Функція `AdaptiveLayout()` визначає макет залежно від орієнтації пристрою:

```kotlin
@Composable
fun AdaptiveLayout(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0E1))
    ) {
        if (isLandscape) {
            // Ландшафтна орієнтація: розміщення фрагментів горизонтально
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    FragmentGreen(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

                    FragmentBrown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }

                FragmentRed(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        } else {
            // Портретна орієнтація: розміщення фрагментів вертикально
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FragmentGreen(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    FragmentBrown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    FragmentRed(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}
```

**Опис блоку `AdaptiveLayout()`:**
Ця композабельна функція відповідає за створення адаптивного інтерфейсу:
1. Функція приймає необов'язковий `modifier` з типом `Modifier` (за замовчуванням порожній `Modifier`)
2. Отримує поточну конфігурацію пристрою через `LocalConfiguration.current`
3. Визначає змінну `isLandscape`, яка дорівнює `true`, якщо пристрій знаходиться в альбомній орієнтації
4. Створює `Box` як контейнер з:
   - Переданим модифікатором
   - Заповненням всього доступного простору (`fillMaxSize()`)
   - Бежевим фоном (`Color(0xFFF5F0E1)`)
5. Використовує умовний оператор `if-else` для зміни макету в залежності від орієнтації:

   **Для ландшафтної орієнтації**:
   - Використовує `Row` (рядок) для розміщення елементів горизонтально
   - У лівій частині екрану створює `Column` (колонку), яка містить:
     - Зелений фрагмент (`FragmentGreen`) зверху
     - Коричневий фрагмент (`FragmentBrown`) знизу
     - Обидва фрагменти займають по 50% висоти колонки (`weight(1f)`)
   - У правій частині екрану розміщує червоний фрагмент (`FragmentRed`) на всю висоту
   - Усі три блоки займають по 50% ширини екрану (`weight(1f)`)

   **Для портретної орієнтації**:
   - Використовує `Column` (колонку) для розміщення елементів вертикально
   - Зверху розміщує зелений фрагмент (`FragmentGreen`) на всю ширину
   - Знизу створює `Row` (рядок), який містить:
     - Коричневий фрагмент (`FragmentBrown`) ліворуч
     - Червоний фрагмент (`FragmentRed`) праворуч
     - Обидва фрагменти займають по 50% ширини рядка (`weight(1f)`)
   - Зелений фрагмент і рядок з двома іншими фрагментами займають по 50% висоти екрану (`weight(1f)`)

Кожен фрагмент реалізовано як окрему `Composable` функцію з унікальним кольором та текстом:

```kotlin
@Composable
fun FragmentGreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF1BA9A6)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 1",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FragmentBrown(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF7A7259)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 2",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FragmentRed(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFE96D5B)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 3",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
```

### FragmentPagerScreen.kt
Цей файл реалізує екран з ViewPager для навігації між фрагментами через горизонтальну прокрутку, а також містить табси (TabRow) згори та панель навігації (NavigationBar) знизу. Це демонструє можливість створення інтерфейсу з декількома вкладками та синхронізованою навігацією.

Основна структура:
```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class TabPage(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FragmentPagerScreen(
    onBackPressed: () -> Unit = {}
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        TabPage("Головна", Icons.Default.Home, Color(0xFF1BA9A6)),
        TabPage("Обране", Icons.Default.Favorite, Color(0xFF7A7259)),
        TabPage("Настройки", Icons.Default.Settings, Color(0xFFE96D5B)),
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Завдання 2: ViewPager") },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                    }
                )

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pages.forEachIndexed { index, tabPage ->
                        Tab(
                            modifier = Modifier.weight(1f),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(text = tabPage.title, maxLines = 1) },
                            icon = { Icon(imageVector = tabPage.icon, contentDescription = null) }
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                pages.forEachIndexed { index, tabPage ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = { Icon(imageVector = tabPage.icon, contentDescription = null) },
                        label = { Text(text = tabPage.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.padding(paddingValues)
        ) { page ->
            val tabPage = pages[page]
            PagerFragmentPage(
                title = tabPage.title,
                backgroundColor = tabPage.color
            )
        }
    }
}
```

**Опис блоку `FragmentPagerScreen()`:**
Ця композабельна функція відповідає за створення екрану з вкладками та горизонтальною прокруткою:
1. Анотації `@OptIn` дозволяють використовувати експериментальні API для Pager та Material 3
2. Функція приймає необов'язковий параметр `onBackPressed` (за замовчуванням порожня функція)
3. Створює та запам'ятовує стан гортача сторінок `pagerState` для відстеження поточної сторінки
4. Створює область корутин `coroutineScope` для виконання асинхронних операцій (анімацій)
5. Визначає список сторінок `pages` з трьох елементів типу `TabPage`, кожен з яких містить:
   - Назву ("Головна", "Обране", "Настройки")
   - Іконку з бібліотеки Material Icons
   - Колір фону (бірюзовий, коричневий, червоний)
6. Налаштовує `Scaffold` з:

   **Верхня панель (topBar):**
   - Містить вертикальну колонку з:
     - `TopAppBar` з заголовком "Завдання 2: ViewPager" та кнопкою повернення
     - `TabRow` зі вкладками, де:
       - `selectedTabIndex` визначає активну вкладку на основі `pagerState.currentPage`
       - Встановлює кольори контейнера та вмісту
       - Займає всю ширину екрану
       - Створює вкладки для кожного елемента списку `pages` через `forEachIndexed`
       - Кожна вкладка має рівну вагу (`weight(1f)`), текст та іконку
       - При натисканні запускає анімований перехід до відповідної сторінки

   **Нижня панель (bottomBar):**
   - Містить `NavigationBar` з навігаційними елементами, синхронізованими з вкладками:
     - Створює `NavigationBarItem` для кожного елемента списку `pages`
     - Відмічає поточний елемент на основі `pagerState.currentPage`
     - При натисканні запускає анімацію переходу до відповідної сторінки
     - Кожен елемент має іконку та текстову мітку

7. У основному вмісті:
   - Створює `HorizontalPager` з:
     - Кількістю сторінок, що дорівнює розміру списку `pages`
     - Прив'язкою до стану `pagerState`
     - Застосуванням відступів від `paddingValues` для коректного розміщення контенту
   - Для кожної сторінки викликає `PagerFragmentPage` з:
     - Заголовком з поточного елемента `TabPage`
     - Кольором фону з поточного елемента `TabPage`

Кожна сторінка представлена через `TabPage` з назвою, іконкою та кольором:

```kotlin
data class TabPage(
    val title: String,
    val icon: ImageVector,
    val color: Color
)
```

Для відображення вмісту фрагментів використовується `PagerFragmentPage`:

```kotlin
@Composable
fun PagerFragmentPage(title: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент: $title",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
```

## Навігація

Додаток використовує Jetpack Navigation для переміщення між екранами:

1. **Головне меню** → **Завдання 1** або **Завдання 2**
2. Кожен екран завдання має кнопку повернення (ArrowBack), яка відправляє користувача назад до головного меню

## Особливості реалізації

### 1. Адаптивний макет
- Використовується `LocalConfiguration.current` для визначення орієнтації пристрою
- Макет динамічно змінюється для оптимального відображення у портретній або ландшафтній орієнтації
- Застосування `Box`, `Row`, `Column` для гнучкого позиціонування елементів
- Використання `modifier.weight()` для пропорційного розподілу простору екрану

### 2. ViewPager з фрагментами
- Використано бібліотеку `com.google.accompanist.pager` для створення HorizontalPager
- Синхронізація між TabRow, NavigationBar та HorizontalPager за допомогою спільного `pagerState`
- Анімовані переходи між фрагментами з використанням `coroutineScope.launch { pagerState.animateScrollToPage(index) }`
- Використання Material3 компонентів для модерного зовнішнього вигляду (TopAppBar, TabRow, NavigationBar)

### 3. Стан та UI оновлення
- Використання Compose State (`rememberPagerState()`) для відстеження поточної сторінки
- Реактивне оновлення інтерфейсу при зміні стану без необхідності ручного оновлення UI
- Підписка на зміни стану через `pagerState.currentPage`

### 4. Jetpack Navigation
- Організація навігації за допомогою `NavHost` та `NavController`
- Передача лямбда-функцій для навігації між екранами (`onNavigateToTask1`, `onNavigateToTask2`)
- Використання `popBackStack()` для повернення на попередній екран

## Детальний аналіз коду

### Використання `remember` та `mutableStateOf`
В додатку широко використовується підхід до керування станом через спостережувані стани:

```kotlin
val pagerState = rememberPagerState()
```

Такий підхід дозволяє:
- Зберігати стан між рекомпозиціями
- Автоматично оновлювати UI при зміні стану
- Робити код більш декларативним

### Використання модифікаторів
Композабельні функції використовують модифікатори для гнучкого налаштування UI:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor),
    contentAlignment = Alignment.Center
) { /* ... */ }
```

Модифікатори застосовуються послідовно, формуючи ланцюжок трансформацій для кожного елемента інтерфейсу.

### Coroutine Scope
Для асинхронних операцій використовується `rememberCoroutineScope()`:

```kotlin
val coroutineScope = rememberCoroutineScope()

// ...
onClick = {
    coroutineScope.launch {
        pagerState.animateScrollToPage(index)
    }
}
```

Це дозволяє виконувати асинхронні дії у відповідь на події користувача, зберігаючи при цьому контроль над життєвим циклом.

## Висновки

В даній лабораторній роботі було успішно реалізовано:

1. **Адаптивний макет**, що автоматично підлаштовується під орієнтацію пристрою, забезпечуючи оптимальне відображення інтерфейсу як у портретному, так і в ландшафтному режимах.

2. **ViewPager з навігацією**, що демонструє використання горизонтальної прокрутки для переміщення між фрагментами, синхронізацію з табами та навігаційною панеллю.

3. **Навігацію між екранами** за допомогою Jetpack Navigation, що забезпечує зручний перехід між різними частинами додатку.

Робота демонструє сучасний підхід до розробки інтерфейсу на Android з використанням Jetpack Compose замість традиційного XML-підходу. Це дозволяє створювати більш динамічні, гнучкі та легкі для підтримки інтерфейси користувача завдяки декларативному стилю програмування.

Використання таких компонентів як `Box`, `Row`, `Column`, `TopAppBar`, `TabRow`, `HorizontalPager` та інших показує, як Jetpack Compose спрощує розробку складних інтерфейсів з
