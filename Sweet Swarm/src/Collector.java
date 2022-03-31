public class Collector extends Bee {
    private int damageDealt = 2;        //Damage dealt by bee

    public Collector(int[][] position){
        super.health = 4;
        super.status = "alive";
        super.beePosition = position;
    }

    protected void controller(){


    }
}
