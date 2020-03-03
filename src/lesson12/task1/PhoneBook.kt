@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    class NumbersForOne(val phoneNums: MutableList<String>)

    private val names: MutableList<String> = mutableListOf()
    private val numbersList: MutableList<NumbersForOne> = mutableListOf()
    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        return if (!names.contains(name)) {
            names += name
            numbersList += mutableListOf(NumbersForOne(mutableListOf("")))
            true
        } else false
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        return if (names.contains(name)) {
            val ind = names.indexOf(name)
            numbersList[ind].phoneNums.clear()
            names[ind] = ""
            if (ind + 2 <= names.size) {
                for (i in ind..names.size - 2) {
                    names[i] = names[i + 1]
                    numbersList[i] = numbersList[i + 1]
                }
                names.remove(names.last())
                numbersList.remove(numbersList.last())
            } else return false
            true
        } else false
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        val ind = names.indexOf(name)
        return if (names.contains(name) && !numbersList[ind].phoneNums.contains(phone)) {
            for (i in 0 until numbersList.size) {
                if (i != ind && numbersList[i].phoneNums.contains(phone))
                    return false
            }
            numbersList[ind].phoneNums.add(phone)
            numbersList[ind].phoneNums.remove("")
            true
        } else false
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        val ind = names.indexOf(name)
        return if (names.contains(name) && numbersList[ind].phoneNums.contains(phone)) {
            numbersList[ind].phoneNums.remove(phone)
            true
        } else false
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        return if (names.contains(name)) {
            numbersList[names.indexOf(name)].phoneNums.toSet()
        } else emptySet()
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for (i in 0 until numbersList.size)
            if (numbersList[i].phoneNums.contains(phone))
                return names[i]
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhoneBook) return false
        for (i in this.names) {
            if (!other.names.contains(i))
                return false
            val ind = other.names.indexOf(i)
            for (j in this.numbersList[this.names.indexOf(i)].phoneNums)
                if (other.numbersList[ind].phoneNums.contains(j))
                    other.numbersList[ind].phoneNums.remove(j)
                else return false
            if (other.numbersList[ind].phoneNums.size != 0) return false
            other.names.remove(i)
        }
        return other.names.size == 0
    }

    override fun hashCode(): Int {
        var result = 0
        for (i in names) {
            for (j in numbersList[names.indexOf(i)].phoneNums)
                result += j.hashCode()
            result += i.hashCode()
        }
        return result
    }
}