Ця програма ілюструє розробку багатопоточної програми на Java, яка виконує асинхронні обчислення для обробки послідовності дійсних чисел. Завдання полягає в тому, щоб асинхронно згенерувати послідовність випадкових чисел та обчислити суму мінімального значення серед чисел на непарних позиціях та максимального значення серед чисел на парних позиціях.

Основні Функції програми: Асинхронна Генерація Послідовності Чисел: Програма асинхронно генерує послідовність із 20 дійсних чисел в діапазоні від 1 до 100, використовуючи клас ThreadLocalRandom. Кожне число форматується до двох знаків після коми перед зберіганням у лог.

Обчислення Мінімального та Максимального Значень: Програма асинхронно обчислює мінімальне значення серед чисел на непарних позиціях та максимальне значення серед чисел на парних позиціях, використовуючи простий перебір масиву.

Виведення Результатів та Обчислення Часу Виконання: Результати обчислень виводяться на екран разом із часом виконання, який вимірюється з моменту старту програми до завершення всіх асинхронних завдань. Час виводиться в мілісекундах із чотирма знаками після коми.
