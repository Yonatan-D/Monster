package exam.config;

public interface ExamConfig {
	int USERTYPE_STD = 1; //�û����ͣ�����
	int USERTYPE_TCH = 2; //�û����ͣ�����Ա
	int USERIDNUM = 4; //�˻�����λ����Ĭ��4λ��
	int TESTAMOUNT_QUICK = 5; //5���⣬�����ȡ������
	int TESTAMOUNT_MOCK = 10; //���濼�ԣ������ȡ������
	int MOCKTEST_TIME = 45*60*1000; //���濼��ʱ������λ������
	int PAGESIZE = 15; //��ѯ���ÿҳ��ʾ��
	String IMAGE_DIR = "TEST_IMG/"; //�洢�ϴ�ͼƬ���ļ�������
}
