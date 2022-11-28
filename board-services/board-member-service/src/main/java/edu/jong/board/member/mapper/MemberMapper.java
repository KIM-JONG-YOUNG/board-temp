package edu.jong.board.member.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.jong.board.member.entiity.MemberEntity;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.response.MemberDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

	@Mapping(target = "username", source = "param.username")
	@Mapping(target = "password", expression = "java(encoder.encode(param.getPassword()))")
	@Mapping(target = "name", source = "param.name")
	@Mapping(target = "gender", source = "param.gender")
	@Mapping(target = "email", source = "param.email")
	MemberEntity toEntity(MemberAddParam param, PasswordEncoder encoder);
	
	MemberDetails toDetails(MemberEntity entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "password", expression = "java(encoder.encode(param.getPassword()))")
	@Mapping(target = "name", source = "param.name")
	@Mapping(target = "gender", source = "param.gender")
	@Mapping(target = "email", source = "param.email")
	MemberEntity updateEntity(MemberModifyParam param, PasswordEncoder encoder, 
			@MappingTarget MemberEntity entity);
	
}
