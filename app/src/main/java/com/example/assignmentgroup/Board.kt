package com.example.assignmentgroup

class Board(gameBoard: MutableList<MutableList<MutableList<Int>>>, freeGrid: Int, freeGridList: MutableSet<Pair<Int, Int>>) {
    var boardGrid = gameBoard
    var boardFreeGrid = freeGrid
    var boardFreeGridList = freeGridList

   /*fun consecutiveCheckers(gridsTaken: MutableList<Int>, columnNumber: Int): Boolean {
        for(i in gridsTaken)
    { /*horizontal win */
        if((i + 1 in gridsTaken) and (i + 2 in gridsTaken) and (i + 3 in gridsTaken))
        {
            println(1)
            return true
        }
        /*vertical win */
        else if((i - columnNumber in gridsTaken) and (i - 2 * columnNumber in gridsTaken) and (i - 3 * columnNumber in gridsTaken))
        {
            println(2)
            return true
        }
        /*sideways win */
        else if((i - (columnNumber + 1) in gridsTaken) and (i - 2 * (columnNumber + 1) in gridsTaken) and (i - 3 * (columnNumber + 1) in gridsTaken))
        {
            println(3)
            return true
        }

        else if((i - (columnNumber - 1) in gridsTaken) and (i - 2 * (columnNumber - 1) in gridsTaken) and (i - 3 * (columnNumber - 1) in gridsTaken))
        {
            println(4)
            return true
        }

    }
    return false
}*/

    fun consecutiveCheckers(board: Board, playerIndex: Int) : Boolean{
        var consecCheckers = false
        var grid = board.boardGrid

        // Horizontal
        for (i in 0..< boardGrid.count()) {
            for (j in 0..< boardGrid[0].count() - 3) {
                if (grid[i][j][1] == playerIndex && grid[i][j + 1][1] == playerIndex && grid[i][j + 2][1] == playerIndex && grid[i][j + 3][1] == playerIndex)
                    consecCheckers = true
            }
        }

        //Vertical
        if (!consecCheckers) {
            for (i in 0..< boardGrid.count() - 3) {
                for (j in 0..< boardGrid[0].count()) {
                    if (grid[i][j][1] == playerIndex && grid[i + 1][j][1] == playerIndex && grid[i + 2][j][1] == playerIndex && grid[i + 3][j][1] == playerIndex)
                        consecCheckers = true
                }
            }
        }

        //Diagonal Left to Right
        if (!consecCheckers) {
            for (i in 0..< boardGrid.count() - 3) {
                for (j in 0..< boardGrid[0].count() - 3) {
                    if (grid[i][j][1] == playerIndex && grid[i + 1][j + 1][1] == playerIndex && grid[i + 2][j + 2][1] == playerIndex && grid[i + 3][j + 3][1] == playerIndex)
                        consecCheckers = true
                }
            }
        }

        //Diagonal Right to Left
        if (!consecCheckers) {
            for (i in 0..< boardGrid.count() - 3) {
                for (j in 3..< boardGrid[0].count()) {
                    if (grid[i][j][1] == playerIndex && grid[i + 1][j - 1][1] == playerIndex && grid[i + 2][j - 2][1] == playerIndex && grid[i + 3][j - 3][1] == playerIndex)
                        consecCheckers = true
                }
            }
        }

        return consecCheckers
    }
}