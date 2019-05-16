package showmethe.github.kframework.adapter.slideAdapter

/**
 * Created by Ken on 2018/9/30.
 */
class MenuItem {
    internal var type: MenuType
    lateinit var stringText: String
    internal var parseTextColor: Int = 0
    internal var backgroundColor: Int = 0


    internal var resId: Int = 0

    constructor(type: MenuType, stringText: String, parseTextColor: Int, backgroundColor: Int) {
        this.type = type
        this.stringText = stringText
        this.parseTextColor = parseTextColor
        this.backgroundColor = backgroundColor
    }

    constructor(type: MenuType, resId: Int, backgroundColor: Int) {
        this.type = type
        this.resId = resId
        this.backgroundColor = backgroundColor
    }
}
