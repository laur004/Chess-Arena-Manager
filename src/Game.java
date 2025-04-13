public class Game {
    private Player white;
    private Player black;
    private GameResult result;


    public void setResult(GameResult gameResult){
        result=gameResult;
    }
    public GameResult getResult() {
        return result;
    }

    public void setWhite(Player player){
        white=player;
    }
    public Player getWhite() {
        return white;
    }

    public void setBlack(Player player) {
        black = player;
    }
    public Player getBlack() {
        return black;
    }

    public String toString(){
        String s=white.getLastName()+" "+white.getFirstName()+" ";
//        System.out.print(game.getWhite().getLastName()+" "+game.getWhite().getFirstName()+" ");
        if(result==null){
            s+=" - ";
            //System.out.print(" - ");
        }
        else {
            switch (result) {
                case W:
                    s+=" 1-0 ";
                    //System.out.print(" 1-0 ");
                    break;
                case B:
                    s+=" 0-1 ";
                    //System.out.print(" 0-1 ");
                    break;

                case D:
                    s+=" 1/2-1/2 ";
                    //System.out.print(" 1/2-1/2 ");
                    break;

                case Z:
                    s+=" 0-0 ";
                    //System.out.print(" 0-0 ");
                    break;

                default:
                    s+=" - ";
                    //System.out.print(" - ");
            }
        }
        s+=black.getLastName()+" "+black.getFirstName()+" ";
        //System.out.println(game.getBlack().getLastName() + " " + game.getBlack().getFirstName() + " ");
        return s;
    }
}
