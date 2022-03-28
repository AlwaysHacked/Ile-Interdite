public class Case {

    public enum State {SEC, INONDE, SUBMERGEE;

        @Override
        public String toString() {
            switch (this) {
                case SEC: return "SEC";
                case INONDE: return "INO";
                case SUBMERGEE: return "SUB";
                default: return "";
            }
        }
    };

    private State etat;

    private Case haut;
    private Case bas;
    private Case gauche;
    private Case droit;

    Case(boolean b){
        if (b)
            etat = State.SEC;
        else
            etat = State.SUBMERGEE;
    }

    Case(){
        new Case(true);
    }

    public void setCases(Case haut, Case bas, Case g, Case d){
        this.haut = haut;
        this.bas = bas;
        this.gauche = g;
        this.droit = d;
    }

    public State getEtat() {
        return etat;
    }

    public void setEtat(State etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return etat.toString();
    }
}
