public class Guard extends Bee {
    public Guard(int[][] position){
        super.health = 4;
        super.status = "alive";
        super.beePosition = position;
    }

    protected void controller(){


    }
}