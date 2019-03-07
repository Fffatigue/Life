package Model;

import Util.Position;

import java.util.ArrayList;
import java.util.List;

public class Life {
    private int m;
    private int n;
    private double impactField[][];
    private boolean aliveField[][];
    private double liveBegin;
    private double liveEnd ;
    private double birthBegin;
    private double birthEnd;
    private double fstImpact;
    private double sndImpact;

    public Life(int m, int n) {
        this.m = m;
        this.n = n;
        impactField = new double[m][n];
        aliveField = new boolean[m][n];
    }

    public void changeCellState(int x, int y, boolean alive) {
        aliveField[x][y] = alive;
        calculateImpacts();
    }

    public void clear(){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                aliveField[i][j] = false;
                impactField[i][j] = 0;
            }
            }
    }

    public double getCellImpact(int x, int y) {
        return impactField[x][y];
    }

    public boolean getCellState(int x, int y) {
        return aliveField[x][y];
    }

    public List<Position> getAllCellsState(){
        List<Position> cells = new ArrayList<>(  );
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                if(aliveField[i][j]) {
                    cells.add( new Position( i, j ) );
                }
            }
        }
        return cells;
    }

    public void setModelParameters(double liveBegin, double liveEnd, double birthBegin, double birthEnd, double fstImpact, double sndImpact) {
        this.liveBegin = liveBegin;
        this.liveEnd = liveEnd;
        this.birthBegin = birthBegin;
        this.birthEnd = birthEnd;
        this.fstImpact = fstImpact;
        this.sndImpact = sndImpact;

    }

    private void calculateImpacts() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                impactField[i][j] = getCountOfFirsts( i, j ) * fstImpact + getCountOfSeconds( i, j ) * sndImpact;
            }
        }
    }

    public void calculateNextState() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(!aliveField[i][j]) {
                    aliveField[i][j] = impactField[i][j] >= birthBegin && impactField[i][j] <= birthEnd;
                }else{
                    aliveField[i][j] = impactField[i][j] >= liveBegin && impactField[i][j] <= liveEnd;
                }
            }
        }
        calculateImpacts();
    }

    private int getCountOfFirsts(int x, int y) {
        int count = 0;
//        int y = cell / columns;
//        int x = cell % columns;
        if (isAlive( x - 1, y )) {
            count++;
        }
        if (isAlive( x + 1, y )) {
            count++;
        }

        if (y % 2 == 1) {
            if (isAlive( x, y - 1 )) {
                count++;
            }
            if (isAlive( x + 1, y - 1 )) {
                count++;
            }
            if (isAlive( x, y + 1 )) {
                count++;
            }
            if (isAlive( x + 1, y + 1 )) {
                count++;
            }

        } else {
            if (isAlive( x, y - 1 )) {
                count++;
            }
            if (isAlive( x - 1, y - 1 )) {
                count++;
            }
            if (isAlive( x - 1, y + 1 )) {
                count++;
            }
            if (isAlive( x, y + 1 )) {
                count++;
            }
        }
        return count;
    }


    private int getCountOfSeconds(int x, int y) {
//        int y = cell / columns;
//        int x = cell % columns;
        int count = 0;
        if (isAlive( x, y - 2 )) {
            count++;
        }
        if (isAlive( x, y + 2 )) {
            count++;
        }
        if (y % 2 == 1) {
            if (isAlive( x - 1, y - 1 )) {
                count++;
            }
            if (isAlive( x - 1, y + 1 )) {
                count++;
            }
            if (isAlive( x + 2, y - 1 )) {
                count++;
            }
            if (isAlive( x + 2, y + 1 )) {
                count++;
            }
        } else {
            if (isAlive( x + 1, y - 1 )) {
                count++;
            }
            if (isAlive( x - 2, y + 1 )) {
                count++;
            }
            if (isAlive( x - 2, y - 1 )) {
                count++;
            }
            if (isAlive( x + 1, y + 1 )) {
                count++;
            }
        }
        return count;
    }

    private boolean isAlive(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n && aliveField[x][y];
    }
}
