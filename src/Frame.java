import java.awt.*;

public class Frame {

    public Frame(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.color = Color.WHITE;
    }

    // Onderstaande functies moeten hier eigenlijk niet zijn uitgewerkt maar wel in de klasses die Frame extenden (=Addresbar en Documentarea)
    // Dus alles wat hier staat is louter prullerij

    public void Paint(Graphics g){
        g.setColor(this.color);
        g.fillRect(this.xPos, this.yPos, this.width, this.height);
        //g.drawRect(this.xPos, this.yPos, this.width, this.height);
        g.setColor(Color.BLACK);

        g.setFont(font);
        g.drawString(this.content, this.xPos, this.yPos+14);
    }

    public void handleMouse(int id, int x, int y, int clickCount){
        if (x > this.xPos && x < (this.width+this.xPos) && y > this.yPos && y < (this.height+this.yPos) && id == 500) {
            //500 wilt zeggen: mouseclicked, als je dat weglaat komen ook mousepressed and mousereleased door
            this.toggleColor();
            System.out.println("You clicked on meee!");
            System.out.println(id);
            System.out.println(x);
            System.out.println(y);
            System.out.println(clickCount);
        }
    }

    // moet hier ook niet zijn geimplementeerd. In Adressbar kan er hier bvb op
    // ingegaan worden als de adressbar al dan niet focus heeft
    public void handleKey(int id, int keyCode, char keyChar){
        System.out.println("Key pressed: id, keyCode, keyChar");
        System.out.println(id);
        System.out.println(keyCode);
        System.out.println(keyChar);
    }

    private void toggleColor(){
        if (this.color == Color.WHITE)
            this.color = Color.BLUE;
        else
            this.color = Color.WHITE;
    }

    Color color;
    private int xPos;
    private int yPos;
    private int width = 800;
    private int height = 20;
    Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 14);
    private String content = "Ik ben een frame met de text helloworld";
}
