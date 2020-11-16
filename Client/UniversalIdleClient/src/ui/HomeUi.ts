

class HomeUi extends eui.Component {
    private btn_bonus: eui.Button

    public constructor() {
        super()
        this.skinName = "HomeUiSkin"

        this.percentHeight = 100
        this.percentWidth = 100

        this.btn_bonus.addEventListener(egret.TouchEvent.TOUCH_TAP, function() {
            Main.client.subscribe('/shop/buy_some/1/2', function (response) {
                console.log("buy result:", response.body);
            });
        }, this)
    }
}