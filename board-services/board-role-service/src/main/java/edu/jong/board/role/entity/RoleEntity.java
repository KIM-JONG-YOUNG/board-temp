package edu.jong.board.role.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.jong.board.jpa.converter.AbstractAttributeConverter;
import edu.jong.board.jpa.entity.BaseTimeEntity;
import edu.jong.board.role.type.APIMethod;
import edu.jong.board.role.type.AntPahtPattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "tb_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;

	@NotBlank
	@Size(max = 30)
	@Pattern(regexp = "^ROLE_[A-Z]+$")
	@Column(unique = true)
	private String name;

	@NotNull
	@Convert(converter = APIMethodAttributeConverter.class)
	private APIMethod method;

	@NotBlank
	@Size(max = 60)
	@AntPahtPattern
	private String urlPattern;

	@Builder
	public RoleEntity(String name, APIMethod method, String urlPattern) {
		super();
		this.name = name;
		this.method = method;
		this.urlPattern = urlPattern;
	}



	@Converter
	public static class APIMethodAttributeConverter extends AbstractAttributeConverter<APIMethod, String> {
		public APIMethodAttributeConverter() {
			super(APIMethod.class, false);
		}
	}
}
