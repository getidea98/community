package top.getidea.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.Page;
import top.getidea.community.dto.PageInfo;
import top.getidea.community.dto.QuestionDTO;
import top.getidea.community.exception.CustomizeException;
import top.getidea.community.exception.CustomizeExceptionCode;
import top.getidea.community.mapper.QuestionMapper;
import top.getidea.community.mapper.UserMapper;
import top.getidea.community.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    /**
     * 简单分页查询
     */
    public PageInfo list(long currentPage, Integer pageSize) {
        PageInfo pageInfo = new PageInfo();
        long count = questionMapper.queryAll(); //查询总的页目数
        long totalPage = 0;
        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        } else if (currentPage < 0) {
            currentPage = 1;
        }
        List<Question> questionList = questionMapper.queryQuestions(5 * (currentPage - 1), pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //根据每一个问题记录，查询对应的user对象；
        //将user对象的avatarUrl添加到QuestionDTO里面
        for (Question question : questionList) {
            List<User> userList = userMapper.queryByAccount(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(userList.get(0));
            questionDTOList.add(questionDTO);
        }
        pageInfo.setQuestionDTOList(questionDTOList);
        pageInfo.init(currentPage, totalPage);
        return pageInfo;
    }

    /**
     * 根据 account（creator）分页查找question记录，返回页面对象
     */
    public PageInfo listById(String account, long currentPage, Integer pageSize) {
        PageInfo pageInfo = new PageInfo();
        Integer count = questionMapper.queryCountByCreator(account);//根据当前用户，查询总的页目数
        pageInfo.setCount(count);
        long totalPage = 0;
        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        } else if (currentPage < 0) {
            currentPage = 1;
        }
        List<Question> questionList = questionMapper.queryLimitByCreator(account,5 * (currentPage - 1),pageSize);//根据当前用户,分页查询
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //根据每一个问题记录，查询对应的user对象；
        //将user对象的avatarUrl添加到QuestionDTO里面
        for (Question question : questionList) {
            List<User> userList = userMapper.queryByAccount(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(userList.get(0));
            questionDTOList.add(questionDTO);
        }
        pageInfo.setQuestionDTOList(questionDTOList);
        pageInfo.init(currentPage, totalPage);
        return pageInfo;
    }

    /**
     * 根据id获取question，随后封装到questionDTO
     */
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.queryById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOTFOUND_PAGE);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        List<User> userList = userMapper.queryByAccount(questionDTO.getCreator());
        if (userList.size() > 0) {
            questionDTO.setUser(userList.get(0));
        }
        System.out.println(questionDTO);
        return questionDTO;
    }

    /**
     * 先判断id是否存在，
     * 1。id == null --》插入该问题记录
     * 2。id != null 问题存在，更新问题：
     * 2。1 获取olQuestion
     * 2。2 将不需要更新的属性，重新赋值给修改后的问题
     * 2。3 将修改后的问题插入数据库
     **/
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            //更新
            Question oldQuestion = questionMapper.queryById(question.getId()); //获取原问题数据
            question.setViewCount(oldQuestion.getViewCount());
            question.setCommentCount(oldQuestion.getCommentCount());
            question.setLikeCount(oldQuestion.getLikeCount());
            question.setGmtCreate(oldQuestion.getGmtCreate());
            question.setGmtModified(System.currentTimeMillis());
            int result = questionMapper.updateById(question);//根据主键，更新数据库
            if (result != 1) {
                throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOTFOUND_PAGE);
            }
        }
    }

    /**
     * 增加浏览数
     */
    public void incView(Integer id,Integer count) {
        questionMapper.updateViewCountById(id,count);
    }

    public Question getQuestionById(Integer id) {
        Question question = questionMapper.queryById(id);
        if(question == null){
            throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOTFOUND_PAGE);
        }
        return question;
    }

    public void incComment(Integer id, Integer count) {
        questionMapper.updateCommentCountById(id,count);
    }

    public PageInfo searchByKey(String key,long currentPage, Integer pageSize){
        List<Question> questionList= questionMapper.queryByKey(key);
        PageInfo pageInfo = new PageInfo();
        long count = questionList.size(); //查询总的页目数
        long totalPage = 0;
        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        } else if (currentPage < 0) {
            currentPage = 1;
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //根据每一个问题记录，查询对应的user对象；
        //将user对象的avatarUrl添加到QuestionDTO里面
        for (Question question : questionList) {
            List<User> userList = userMapper.queryByAccount(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(userList.get(0));
            questionDTOList.add(questionDTO);
        }
        pageInfo.setQuestionDTOList(questionDTOList);
        pageInfo.init(currentPage, totalPage);
        return pageInfo;
    }
}
