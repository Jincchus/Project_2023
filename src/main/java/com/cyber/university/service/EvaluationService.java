package com.cyber.university.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyber.university.dto.EvaluationDto;
import com.cyber.university.dto.EvaluationInfoDto;
import com.cyber.university.handler.exception.CustomRestfullException;
import com.cyber.university.repository.interfaces.EvaluationRepository;
import com.cyber.university.repository.interfaces.StuStatRepository;
import com.cyber.university.repository.model.Question;
import com.cyber.university.utils.Define;

import lombok.extern.slf4j.Slf4j;

/**
  * @FileName : EvaluationService.java
  * @Project : CyberUniversity
  * @Date : 2024. 3. 19. 
  * @작성자 : 박경진
  * @변경이력 :
  * @프로그램 설명 : 강의평가 서비스
  */
@Service
@Slf4j
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	
	
	/**
	  * @Method Name : findEvaluationInfoByStudentId
	  * @작성일 : 2024. 3. 19.
	  * @작성자 : 박경진
	  * @변경이력 : 
	  * @Method 설명 : 강의 평가를 위한 강의 및 교수, 학생 정보 조회 
	  */
	public EvaluationInfoDto findEvaluationInfoByStudentId(Integer subjectId,Integer studentId) {
		
		log.info("evaluation service in");

		Map<String, Object> map = new HashMap<>();
		map.put("subjectId", subjectId);
		map.put("studentId", studentId);
		
		EvaluationInfoDto evaluationInfoDto = evaluationRepository.selectEvaluationInfoByStudentId(map);

		log.info("evaluation service evaluation info dto : " + evaluationInfoDto);
		return evaluationInfoDto;
	}



	/**
	  * @Method Name : findAllQuestion
	  * @작성일 : 2024. 3. 19.
	  * @작성자 : 박경진
	  * @변경이력 : 
	  * @Method 설명 : 강의평가 질문 항목
	  */
	public Question findQuestion() {
		Question question = evaluationRepository.selectQuestion();
		return question;
	}



	/**
	  * @Method Name : insertEvaluation
	  * @작성일 : 2024. 3. 20.
	  * @작성자 : 박경진
	  * @변경이력 : 
	  * @Method 설명 : 강의평가 등록
	  */
	@Transactional
	public int insertEvaluation(EvaluationDto dto) {
		// 여기서 select로 studentId와 subjectId 조회 후 dto의 sutdentId와 subjectId 랑 비교 후 조회하면 이미 등록되어있음 -> 등록되면 안됨
		Map<String, Object> map = new HashMap<>();
		map.put("studentId", dto.getStudentId());
		map.put("subjectId", dto.getSubjectId());
		int evalauationResult = evaluationRepository.selectEvaluationByStudentIdAndSubjectId(map);
		if(evalauationResult >= 1) {
			throw new CustomRestfullException(Define.ALREADY_EVALUATION, HttpStatus.BAD_REQUEST);
		}
		
		log.info("evaluation service dto는 들어왓나요?"+dto);
		int result = evaluationRepository.insertEvaluation(dto);
		log.info("evaluation service 레포지토리는 다녀왔나요?");
		if(result == 1) {
			// TODO: 여기서 insert 이후 evaluation_id값을 구해서 stu_sub 테이블에 evaluation_id값을 update해 줄 거야!
			//StuStatRepository.updateEvaluationId();
		}
		
		
		return result;
		
	}



	/**
	  * @Method Name : findEvaluationByStudnetIdAndSubjectId
	  * @작성일 : 2024. 3. 20.
	  * @작성자 : 박경진
	  * @변경이력 : 
	  * @Method 설명 :
	  */
	public Object findEvaluationByStudnetIdAndSubjectId(Integer userId, Integer subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

}
