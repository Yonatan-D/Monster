package exam.config;

public interface ExamConfig {
	int USERTYPE_STD = 1; //用户类型：考生
	int USERTYPE_TCH = 2; //用户类型：管理员
	int USERIDNUM = 4; //账户号码位数（默认4位）
	int TESTAMOUNT_QUICK = 5; //5题快测，随机抽取的题数
	int TESTAMOUNT_MOCK = 10; //仿真考试，随机抽取的题数
	int MOCKTEST_TIME = 45*60*1000; //仿真考试时长，单位：毫秒
	int PAGESIZE = 15; //查询结果每页显示数
	String IMAGE_DIR = "TEST_IMG/"; //存储上传图片的文件夹名称
}
