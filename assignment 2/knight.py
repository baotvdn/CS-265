#!/usr/bin/env python
#Bao Dang


import sys
import random
class knight_tours:
	# The number of rows and columns are put into the class knight_tours
	def __init__(self, row, column):
		self.row = row
		self.column = column
		self.success = True
		self.board = []
		""" The variable "success" defines if the knight can make it to cover the board
				The variable "board" contains the position on the board """

	# Create a board and fill in the position with "x"
	def create_board(self):
		self.board = [["x" for c in range(self.column)] for r in range(self.row)]
		self.board[0][0] = 1
		#Set the first square is the first move
		return self.board
		
	#Use recursion to move the knight to cover the board
	def move(self, current_position=[0,0], move=1):
	#Set default current position be the first square which is marked as number "1" on the board
		move += 1
		possible_move = []
		all_moves = [[1,2], [1,-2], [-1, 2], [-1, -2], [2, 1], [2, -1], [-2, 1], [-2, -1]]

		for pos in all_moves:
			next_possible_row = current_position[0] + pos[0]
			next_possible_column = current_position[1] + pos[1]
			if next_possible_row >= 0 and next_possible_column >= 0:
				# The position should be inside the size of the board. It means the row and column should be positive and less than the size of the board
				try:
					if (self.board[next_possible_row][next_possible_column] == "x") :
					# The knight can only move to the position which is marked as "x"
						possible_move.append([next_possible_row,next_possible_column])
				except IndexError:
					pass

		# Replace the current position by the next position and mark the next position to be the next number
		if len(possible_move)> 0:
			next_position = possible_move[random.randint(0, len(possible_move) - 1)]
			self.board[next_position[0]][next_position[1]] = move
			self.move(next_position, move)

	# Check if there is any position the knight cannot cover on the board
	def success_or_failure(self):
		for r in self.board:
			for c in r:
				if str(c) == "x":
					self.success = False
		return self.success

	# Print the knight tour result
	def print_result(self):
		if self.success:
			print "SUCCESS:"
		else:
			print "FAIL:"
			
		for r in self.board:
			for c in r:
				print "%3s" % str(c),
			print ""

if __name__ == '__main__':
	if len(sys.argv) != 4:
		print "Too many or too few arguments. There should be three arguments"
		exit()
	else:
		try:
			row = int(sys.argv[1])
			column = int(sys.argv[2])
			attemp = int(sys.argv[3])
		except ValueError:
			print "Arguments should be numbers"
			exit()
	kt = knight_tours(row,column)
	kt.success = False
	while attemp != 0 and kt.success == False:
		kt = knight_tours(row,column)
		kt.create_board()
		kt.move()
		kt.success_or_failure()
		attemp-=1
	kt.print_result()	#Print the last result
