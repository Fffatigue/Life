package Controller;

class Position {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Position.class.isAssignableFrom( obj.getClass() )) {
            return false;
        }

        final Position other = (Position) obj;
        return other.getX() == x && other.getY() == y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + ((Integer) this.x).hashCode();
        hash = 53 * hash + ((Integer) this.y).hashCode();
        return hash;
    }
}
