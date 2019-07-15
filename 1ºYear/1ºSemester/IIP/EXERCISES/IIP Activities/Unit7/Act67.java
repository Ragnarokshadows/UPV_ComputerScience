package Unit7;

public class Act67{
    public class Set {
        private boolean [] set;
        private int last;
        
        /** Constructor of empty set, which will
        * contain natural numbers in the interval [0..l]. */
        public Set(int l) {
            set = new boolean[l + 1];
            last = l;
        }
        
        /** Check pertaining to the set of
        * a given x , 0<=x<=last. */
        public boolean pertain(int x) { return set[x]; }
        
        /** Gives cardinal of the set. */
        public int cardinal() {
            int card = 0;
            for (int i = 0; i < set.length; i++){
                if (set[i]){ card++;}
            }
            return card;
        }
        
        /** Add to the set a given x, 0<=x<=last. */
        public void add(int x) { set[x] = true; }
        
        /** Generate random elements in the current set. */
        public void randomSet() {
            for (int i = 0; i < set.length; i++){
                set[i] = (Math.random() >= 0.5);
            }
        }
        
        public Set union(Set s) {
            int i;
            Set res = new Set(Math.max(this.last, s.last));
            
            for (i = 0;i <= res.last; i++){
                if ((this.pertain(i) || s.pertain(i))){
                    res.add(i);
                }
            }
            
            return res;
        }
        
        public Set intersection(Set s) {
            int i;
            Set res = new Set(Math.min(this.last, s.last));
            
            for (i = 0;i <= res.last; i++){
                if (this.pertain(i) && s.pertain(i)){
                    res.add(i);
                }
            }
            
            return res;
        }
        
        public Set difference(Set s) {
            int i;
            Set res = new Set(this.last);
            
            for (i = 0;i <= res.last; i++){
                if ((i <= this.last && this.pertain(i)) &&
                (i <= s.last && !s.pertain(i))){
                    res.add(i);
                }
            }
            
            return res;
        }
        
        public Set complementary() {
            int i;
            Set res = new Set(this.last);
            
            for (i = 0;i <= res.last; i++){
                if (!this.pertain(i)){
                    res.add(i);
                }
            }
            
            return res;
        }
    }
}