package edu.jong.board.member.entiity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.jong.board.BoardConstants.TableNames;
import edu.jong.board.jpa.converter.AbstractAttributeConverter;
import edu.jong.board.jpa.entity.BaseTimeEntity;
import edu.jong.board.member.type.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = TableNames.TB_MEMBER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@NotNull
	@Size(max = 30)
	@Column(unique = true)
	private String username;
	
	@Setter
	@NotNull
	@Size(max = 60)
	private String password;
	
	@Setter
	@NotNull
	@Size(max = 30)
	private String name;
	
	@Setter
	@NotNull
	@Column(length = 1)
	@Convert(converter = GenderAttributeConverter.class)
	private Gender gender;
	
	@Setter
	@NotNull
	@Email
	@Size(max = 60)
	private String email;

	@Builder
	public MemberEntity(String username, String password, String name, Gender gender, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.email = email;
	}

	@Converter
	public static class GenderAttributeConverter extends AbstractAttributeConverter<Gender, Character> {
		
		public GenderAttributeConverter() {
			super(Gender.class, false);
		}
	}
}
