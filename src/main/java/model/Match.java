package model;

public class Match {

    /**
     * Počet sirek.
     */
    private static int totalNum = 0;

    /**
     * Aktuální index.
     */
    private int index;

    /**
     * Viditelnost.
     */
    private boolean visibility;


    public Match(){
        this(Match.totalNum++);
    }

    public Match(int index){
        this.index = index;
        this.visibility = true;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
