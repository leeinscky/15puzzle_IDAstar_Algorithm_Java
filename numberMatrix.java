public class numberMatrix{
 
    public int numList[] = new int[16];            
    public int numberToSite[][] = new int[16][2];  
    
	public int getInversionNum(){ //getInversionNum函数：获得逆序数，用于判断初始数码是否有解
        int inverNum = 0;
        for(int i=0; i<=15; i++)
		{
            if(numList[i]==0) continue; //将数码16个数中的0去掉（结束当前循环）
            for(int j=i+1; j<=15; j++)
			{
                if(numList[j] == 0) continue;
                if(numList[i] > numList[j]){
                    inverNum++;
                }
            }
        }
        return inverNum;
    }
	
	public boolean solvable(numberMatrix puzzle){
        int inverNum1 = getInversionNum();
        int inverNum2 = puzzle.getInversionNum();
		int inverNum = inverNum1+inverNum2;
        int lineDiff = Math.abs(numberToSite[0][0]-puzzle.numberToSite[0][0]);
        if(lineDiff%2 == 1) //初始数码空格处（0数字）所在的行数与最终数码空格处所在的行数两者之差的绝对值为奇数
		{
			if(inverNum%2 == 1){ //初始数码数字序列的逆序数和最终数码数字序列的逆序数的和为奇数，即二者奇偶性不同，有解
                return true;
            }else{
                return false;
			}
        }else              //初始数码空格处（0数字）所在的行数与最终数码空格处所在的行数两者之差的绝对值为偶数
		{
		    if(inverNum%2 == 0){ //初始数码数字序列的逆序数和最终数码数字序列的逆序数的和为偶数，即二者奇偶性相同，有解
			    return true;
			}else{
				return false;
			}
        }
    }
	
	public int Manhattan_Distance(numberMatrix puzzle)//获得曼哈顿距离（评估函数初始值）
	{
        int maDistance=0;
        for(int k=1; k<=15; k++){
            maDistance += Math.abs( numberToSite[k][0] - puzzle.numberToSite[k][0]) + Math.abs(numberToSite[k][1] - puzzle.numberToSite[k][1] );
        }
        return maDistance;
    }
	
	public numberMatrix (int arrayList[]) { //numberMatrix类的构造函数
        for(int i=0; i<=15; i++)
		{
            numList[i]=arrayList[i];
        }
        numberToSite_init();
    }
	
	//numberToSite_init函数：把list中不同数的位置对应到numberToSite中
	public void numberToSite_init(){  
        for(int i=0; i<=15; i++){
            numberToSite[numList[i]][0] = i/4;
            numberToSite[numList[i]][1] = i%4;
        }
    }

	public void type() {
        for(int i=0; i<=15; i=i+4)
		{
            System.out.printf( "[ %d %d %d %d ] %n",numList[i],numList[i+1],numList[i+2],numList[i+3]);
        }
        System.out.println("*******************************");
    }

	//exchange函数：交换数码表中两个数的位置（对调）
	public void exchange( int oldRaw, int oldColumn, int newRaw, int newColumn ){ 
        int listLocation1 = oldRaw*4 + oldColumn;
        int listLocation2 = newRaw*4 + newColumn;
		
		numberToSite[numList[listLocation1]][0] = newRaw;
        numberToSite[numList[listLocation1]][1] = newColumn;
	    numberToSite[numList[listLocation2]][0] = oldRaw;
        numberToSite[numList[listLocation2]][1] = oldColumn;
		
        int tempList = numList[listLocation1];
	    numList[listLocation1] = numList[listLocation2];
	    numList[listLocation2] = tempList;
    }

}
