//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Scanner;
//
//public class Ranking {
//    private class TournamentPlayer {
//        private Player player;
//        private double points;
//
//        public TournamentPlayer(Player player, double points) {
//            this.player = player;
//            this.points = points;
//        }
//
//        public Player getPlayer() {
//            return player;
//        }
//
//        public double getPoints() {
//            return points;
//        }
//
//        public void setPoints(double points) {
//            this.points = points;
//        }
//    }
//
//    private ArrayList<TournamentPlayer> ranking;
//
//    public Ranking() {
//        ranking = new ArrayList<>();
//    }
//
//    public void showRanking(){
//        for(int i=0;i<ranking.size();i++){
//            System.out.println((i+1)+" "+ranking.get(i).player+" Points: "+ranking.get(i).getPoints());
//        }
//    }
//
//    public void addPlayer(Player player) {
//        ranking.add(new TournamentPlayer(player, 0));
//        sort();
//    }
//
//    public void setPointsToAllPlayers(){
//        Scanner obj=new Scanner(System.in);
//        double points;
//        System.out.println("Set points for each player:");
//        for(TournamentPlayer tp:ranking){
//            System.out.println(tp.getPlayer());
//            System.out.print("Points: ");
//            points= Double.parseDouble(obj.nextLine());
//            tp.setPoints(points);
//        }
//        sort();
//    }
//
//    private void sort() {
//        Collections.sort(ranking, new Comparator<TournamentPlayer>() {
//            @Override
//            public int compare(TournamentPlayer e1, TournamentPlayer e2) {
//                return Double.compare(e2.getPoints(), e1.getPoints());
//            }
//        });
//    }
//
//}
