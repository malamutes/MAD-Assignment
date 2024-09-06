package com.example.assignmentgroup

class Board(gameBoard: MutableList<MutableList<MutableList<Int>>>, freeGrid: Int, freeGridList: MutableSet<Pair<Int, Int>>) {
    var boardGrid = gameBoard
    var boardFreeGrid = freeGrid
    var boardFreeGridList = freeGridList

    fun consecutiveCheckers(grisTaken: MutableList<Int>, columnNumber: Int): Boolean {

        for(i in grisTaken)
        { /*horizontal win */
            if((i + 1 in grisTaken) and (i + 2 in grisTaken) and (i + 3 in grisTaken))
            {
                println(1)
                return true
            }
            /*vertical win */
            else if((i - columnNumber in grisTaken) and (i - 2 * columnNumber in grisTaken) and (i - 3 * columnNumber in grisTaken))
            {
                println(2)
                return true
            }
            /*sideways win */
            else if((i - (columnNumber + 1) in grisTaken) and (i - 2 * (columnNumber + 1) in grisTaken) and (i - 3 * (columnNumber + 1) in grisTaken))
            {
                println(3)
                return true
            }

            else if((i - (columnNumber - 1) in grisTaken) and (i - 2 * (columnNumber - 1) in grisTaken) and (i - 3 * (columnNumber - 1) in grisTaken))
            {
                println(4)
                return true
            }

        }
        return false
    }
}