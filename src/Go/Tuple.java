package Go;

class Tuple{
    int a;
    int b;
    Tuple(int a, int b){
        this.a=a;
        this.b=b;
    }

    public String toString() {
        return "(" +a +"," + b+")";
    }

    @Override
    public boolean equals(Object a) {
        boolean equals = false;
        if (a != null && a instanceof Tuple){
            Tuple t = (Tuple) (a);
            equals =  this.a==t.a && this.b ==t.b;
        }
        return equals;
    }

    public int hashCode() {
      int result = 17;
      result = 31 * result + a + 31*result + b;
      return result;
    }
    public Tuple clone(Tuple t ){
        return new Tuple(t.a, t.b);
    }
}
