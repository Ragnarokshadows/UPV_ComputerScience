import sys

def getKey(item):
    return item[1]

if __name__ == '__main__':
	num_cases = int(input())
	blank = input()
	for case in range(num_cases):
		list_of_pics=[]
		fee = [int(x) for x in sys.stdin.readline().split()]
		while True:
			pic = sys.stdin.readline().strip()
			if pic:
				list_of_pics.append(pic.split())
			else:
				break
		
		list_of_pics = sorted(list_of_pics, key = getKey)

		#all read and sorted

		register ={}
		for pic in list_of_pics:
			if pic[0] in register:
				val = register[pic[0]]
				if (val[0] and pic[2] in 'enter') or (not val[0] and pic[2] in 'exit'):
					val.append(pic[1:])
					val[0] = not val[0]
				elif (not val[0] and pic[2] in 'enter'):
					val[-1] = pic[1:]
			else:
				if pic[2] in 'enter':
					register[pic[0]] = [False, pic[1:]]

		for matricula in sorted(register.keys()):
			aux = 2
			data = register[matricula][1:]
			if len(data)>1:
				for i in range(0, len(data), 2):
					if i == len(data)-1:
						continue
					hora = int(data[i][0].split(":")[2])
					aux+=((fee[hora]/100)*abs(int(data[i][2])-int(data[i+1][2]))+1)
				print(matricula, '$'+format(aux, '.2f'))
		if case < num_cases-1:
			print()