

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

        this.viewstack.removeChildren()

        this.viewstack.addChild(new HomeUi())
        this.viewstack.addChild(new InventoryUi())
        this.viewstack.addChild(new BattleUi())
        this.viewstack.addChild(new CharacterUi())
        this.viewstack.addChild(new SquardUi())

        this.viewstack.selectedIndex = 0


        this.btn_home.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 0
        }, this)

        this.btn_inventory.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 1
        }, this)

        this.btn_battle.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 2
        }, this)

        this.btn_character.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 3
        }, this)

        this.btn_squard.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            this.viewstack.selectedIndex = 4
        }, this)
    }
}