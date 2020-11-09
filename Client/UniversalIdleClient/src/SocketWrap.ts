
class SocketWrap {
    private webSocket : egret.WebSocket;

    public constructor(ws: egret.WebSocket, url: string) {
        this.webSocket = ws;
        this.webSocket.addEventListener(egret.ProgressEvent.SOCKET_DATA, this.onReceiveMessage, this);                            
        this.webSocket.addEventListener(egret.Event.CONNECT, this.onSocketOpen, this);   
        this.webSocket.addEventListener(egret.Event.CLOSE, this.onSocketClose, this);
        this.webSocket.addEventListener(egret.IOErrorEvent.IO_ERROR, this.onSocketError, this);

        this.url = url;
    }

    public url : any = null;

    public binaryType : any = null;

    public onmessage : Function = null;

    public onclose : Function = null;

    public onopen : Function = null;

    private onSocketOpen():void {
        // this.send("test")
        // this.send("ok")

        if(this.onopen) {
            this.onopen();
        }
    }

    private onSocketClose():void {
        if(this.onclose) {
            this.onclose();
        }
    }
    
    private onSocketError(e: egret.Event):void {
        console.log("webSocket异常")
        console.log(e)

        if(this.onclose) {
            this.onclose();
        }
    }

    private onReceiveMessage(e:egret.Event):void {    
        var msg = this.webSocket.readUTF();    
        console.log("收到数据：" + msg);
        if(this.onmessage) {
            this.onmessage({data: msg})
        }
    }

    public send(payload: any) {
        console.log("send:" + payload)
        this.webSocket.writeUTF(payload)
        this.webSocket.flush()
    }

    public close() {
        this.webSocket.close()
    }
}