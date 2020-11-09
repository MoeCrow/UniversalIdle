
class SocketWrap {
    private webSocket : egret.WebSocket;

    public constructor(ws: egret.WebSocket, url: string) {
        this.webSocket = ws;
        this.webSocket.type = egret.WebSocket.TYPE_BINARY
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
        this.send("CONNECT\x0Aaccept-version:1.1,1.0\x0Aheart-beat:10000,10000\x0A\x0A\x00")
        // this.send("ok\x0Ab:b\x0A\x0A\x00")

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
        this.webSocket.type = egret.WebSocket.TYPE_STRING
        var msg = this.webSocket.readUTF();    
        this.webSocket.type = egret.WebSocket.TYPE_BINARY
        console.log("收到数据：" + msg);
        if(this.onmessage) {
            this.onmessage({data: msg})
        }
    }

    private stringToByte(str):Uint8Array {
        var bytes = new Array();
        var len, c;
        len = str.length;
        for(var i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if(c >= 0x010000 && c <= 0x10FFFF) {
                bytes.push(((c >> 18) & 0x07) | 0xF0);
                bytes.push(((c >> 12) & 0x3F) | 0x80);
                bytes.push(((c >> 6) & 0x3F) | 0x80);
                bytes.push((c & 0x3F) | 0x80);
            } else if(c >= 0x000800 && c <= 0x00FFFF) {
                bytes.push(((c >> 12) & 0x0F) | 0xE0);
                bytes.push(((c >> 6) & 0x3F) | 0x80);
                bytes.push((c & 0x3F) | 0x80);
            } else if(c >= 0x000080 && c <= 0x0007FF) {
                bytes.push(((c >> 6) & 0x1F) | 0xC0);
                bytes.push((c & 0x3F) | 0x80);
            } else {
                bytes.push(c & 0xFF);
            }
        }
        return new Uint8Array(bytes);
    }

    public send(payload: string) {
        console.log("send:" + payload)
        // this.webSocket.writeUTF(payload)
        this.webSocket.writeBytes(new egret.ByteArray(this.stringToByte(payload)))
        this.webSocket.flush()
    }

    public close() {
        this.webSocket.close()
    }
}