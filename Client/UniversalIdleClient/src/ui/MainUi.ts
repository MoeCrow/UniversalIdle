

class MainUi extends eui.Component {
    private btn_home: eui.Button
    private btn_inventory: eui.Button
    private btn_battle: eui.Button
    private btn_character: eui.Button
    private btn_squard: eui.Button

    private viewstack: eui.ViewStack

    public constructor() {
        super()
        this.skinName = "MainUiSkin"
    }

    protected createChildren() {
        super.childrenCreated();

        this.btn_home.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 0
        }, this)

        this.btn_inventory.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 1
        }, this)

        this.btn_battle.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 2
        }, this)
    }
}