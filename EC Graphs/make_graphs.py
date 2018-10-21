import matplotlib.pyplot as plt
import numpy as np

with open('avgFitnessPerGen.txt') as f:
	content = f.readlines()

idx = -1
x = [np.array([]) for i in range(3)]

for line in content:
	if line[0]=='A':
		idx+=1
		continue
	else:
		x[idx] = np.append(x[idx], float(line))

plt.subplot(1,5,1)
plt.grid()
plt.xlabel('Generation #')
plt.ylabel('Avg. Fitness')
plt.title('Bent Cigar - Meta-GA')
plt.plot(1+np.arange(x[0].size), x[0])

plt.subplot(1,5,2)
plt.grid()
plt.xlabel('Generation #')
plt.title('Schaffers - Meta-GA')
plt.plot(1+np.arange(x[1].size), x[1])

plt.subplot(1,5,5)
plt.grid()
plt.xlabel('Generation #')
plt.title('Katsuura - Grid Search')
plt.plot(1+np.arange(x[2].size), x[2])

with open('avgFitnessPerGen_hand.txt') as f:
	content = f.readlines()

idx = -1
x = [np.array([]) for i in range(2)]

for line in content:
	if line[0]=='A':
		idx+=1
		continue
	else:
		x[idx] = np.append(x[idx], float(line))

plt.subplot(1,5,3)
plt.grid()
plt.xlabel('Generation #')
plt.title('Bent Cigar - Grid Search')
plt.plot(1+np.arange(x[0].size), x[0])

plt.subplot(1,5,4)
plt.grid()
plt.xlabel('Generation #')
plt.title('Schaffers - Grid Search')
plt.plot(1+np.arange(x[1].size), x[1])

plt.show()