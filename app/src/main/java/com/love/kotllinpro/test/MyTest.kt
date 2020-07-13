package com.love.kotllinpro.test

import android.content.Context
import android.content.SharedPreferences
import com.love.kotllinpro.app.App

import com.tencent.bugly.proguard.t
import org.jetbrains.annotations.TestOnly
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MyTest {
    data class Student(var name: String = "李白", var number: Int = 1)

    private val list: MutableList<Student> by lazy {
        mutableListOf(
            Student("A", 1),
            Student("B", 2),
            Student("C", 3),
            Student("D", 4)
        )
    }

    @TestOnly
    fun test1() {
        var s = list.map {
            it.name
        }.joinToString(separator = "/").apply {
            println(this)
        }

    }

    fun Int.r(): RationalNumber = RationalNumber(this, 1)
    fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)
    data class RationalNumber(val numerator: Int, val denominator: Int)

    fun getAList(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        arrayList.sortWith(Comparator { x, y -> y - x })
        return arrayList
    }

    fun getMyList(): List<Int> {
        return arrayListOf(1, 5, 2).sortedDescending()
    }


    operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

    class DateRange(override val start: MyDate, override val endInclusive: MyDate) :
        ClosedRange<MyDate>

    fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
        return date in first..last
    }

    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate) = when {
            year != other.year -> year - other.year  //首先比较年
            month != other.month -> month - other.month//然后比较月
            else -> dayOfMonth - other.dayOfMonth//比较日
        }

    }

    fun compare(date1: MyDate, date2: MyDate) = date1 < date2

    interface Base {
        fun printMessage()
        fun printMessageLine()
    }
    class BaseImpl(val x: Int) : Base {
        override fun printMessage() {
            print(x)
        }
        override fun printMessageLine() {
            print(x)
        }
    }
    class Derived(b: Base) : Base by b {
        override fun printMessage() {
            print("abc")
        }
    }

    interface Image {
        fun display()
    }

    class RealImage : Image {
        override fun display() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class ProxyImage : Image {
        private val realImage: RealImage by lazy {
            RealImage()
        }

        override fun display() {

            val jane = User(name = "Jane", age = 35)
            val (name, age) = jane
            println("$name, $age years of age") // prints "Jane, 35 years of age"
            realImage?.display() ?: print("empty")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    data class User(val name: String, val age: Int)

    sealed class Uiop {
        object Show : Uiop()
        object Hide : Uiop()
    }

    enum class TYPE {
        RED,
        GREEN,
        BLUE
    }

    /**
     * 扩展属性不能直接初始化，而是通过get
     */
    val <T>List<T>.lastIndex: Int
        get() = size - 1

    /**
     * 扩展函数
     */
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        var temp = this[index1]
        this[index1] = this[index2]
        this[index1] = temp;
        this.lastIndex
    }

    fun Any?.toString(): String {
        if (this == null) {
            return "null"
        }
        return toString();
    }

    class MyClass {
        companion object {

        }
    }

    fun MyClass.Companion.fool() {

    }

    val MyClass.Companion.no: Int
        get() = 10


    class D : C() {
        fun bar() {
            println("d BAR")
        }
    }

    /**
     * 不加open是无法被继承的
     */
    open class C {
        var name: String? = null
            get() {
                return field + "34r54tg"
            }
            set(value) {
                field = value + "4t4"
            }

        fun baz() {
            println("C bar")
        }

        fun D.foo() {  //扩展函数
            baz()
        }

        fun caller(d: D?) {
            d?.foo()
        }
    }

    class Human : MyInterface {
        var d: String
            get() = "0"
            set(value) {
                foo(this);
            }

        companion object {
            val a: Int = 1
            val b: String = "1234"
            fun test() {

            }

            fun say() {

            }
        }

        fun Human.Companion.foo(human: Human) {

        }

        override var name: String
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}

        override fun foo() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface MyInterface {
        var name: String
        open fun foo()
        fun bar() {
            print("fdffrf")
        }
    }

    class Box<T>(t: T) {
        var value = t
    }

    /**
     * 对象编导是
     */
    fun testObject() = object {
        var x: Int = 2
        var a: String = ""

    }

    /**
     * 对象表达式
     */
    object tttyyy {

    }

    /**
     * 把函数赋值给变量吗，这是对象函数lambda写法
     */
    var hello: (Int) -> Boolean = { it > 0 }

    /**
     * 函数的参数是函数这是高阶函数
     */
    fun apple(a: Int, funParam: (Int) -> String): String {
        return funParam(2) + a
    }

    /**
     * 函数的返回值为函数，这也是高阶函数
     */
    fun bannana(bar: Int = 0, baz: Int): (String) -> Int {
        return this::hhhh
    }

    fun hhhh(a: String): Int {
        bannana(baz = 1)
        return 1
    }

    /**
     * 函数写法var a=hello(a:int){
     *
     */
    private fun hello(a: Int = 2): Boolean {
        return a > 0
    }

    fun printKotllin(name: String?, vararg a: String) {

    }

    //var hello: (Int) -> Boolean = { it > 0 }
    fun main(args: Array<String>) {

        hello(2)
        "2".testinfix("2")
        hello.invoke(2)
    }

    /**
     * 中缀表达式
     */
    infix fun String.testinfix(a: String) {
        //局部函数
        fun localFun() {

        }
    }



    class Delegate{
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
           return "a"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, s: String) {
               message=s
        }

        private var message="Default Msg"

    }
    class Example{
        var msg: String by Delegate()

        val hel:String by lazy {
            "3r434"
        }
    }

}






