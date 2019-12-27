public class numberMatrix{
 
    public int numList[] = new int[16];            
    public int numberToSite[][] = new int[16][2];  
    
	public int getInversionNum(){ //getInversionNum����������������������жϳ�ʼ�����Ƿ��н�
        int inverNum = 0;
        for(int i=0; i<=15; i++)
		{
            if(numList[i]==0) continue; //������16�����е�0ȥ����������ǰѭ����
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
        if(lineDiff%2 == 1) //��ʼ����ո񴦣�0���֣����ڵ���������������ո����ڵ���������֮��ľ���ֵΪ����
		{
			if(inverNum%2 == 1){ //��ʼ�����������е������������������������е��������ĺ�Ϊ��������������ż�Բ�ͬ���н�
                return true;
            }else{
                return false;
			}
        }else              //��ʼ����ո񴦣�0���֣����ڵ���������������ո����ڵ���������֮��ľ���ֵΪż��
		{
		    if(inverNum%2 == 0){ //��ʼ�����������е������������������������е��������ĺ�Ϊż������������ż����ͬ���н�
			    return true;
			}else{
				return false;
			}
        }
    }
	
	public int Manhattan_Distance(numberMatrix puzzle)//��������پ��루����������ʼֵ��
	{
        int maDistance=0;
        for(int k=1; k<=15; k++){
            maDistance += Math.abs( numberToSite[k][0] - puzzle.numberToSite[k][0]) + Math.abs(numberToSite[k][1] - puzzle.numberToSite[k][1] );
        }
        return maDistance;
    }
	
	public numberMatrix (int arrayList[]) { //numberMatrix��Ĺ��캯��
        for(int i=0; i<=15; i++)
		{
            numList[i]=arrayList[i];
        }
        numberToSite_init();
    }
	
	//numberToSite_init��������list�в�ͬ����λ�ö�Ӧ��numberToSite��
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

	//exchange�������������������������λ�ã��Ե���
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
