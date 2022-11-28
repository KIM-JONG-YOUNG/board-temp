package edu.jong.board.jpa.converter;

import java.util.EnumSet;

import javax.persistence.AttributeConverter;

import edu.jong.board.type.CodeEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractAttributeConverter<E extends Enum<E> & CodeEnum<V>, V> implements AttributeConverter<E, V> {
	
	private final Class<E> enumClass;
	private final boolean nullable;
	
	@Override
	public V convertToDatabaseColumn(E attribute) {

		if (!nullable && attribute == null) 
			throw new IllegalArgumentException(
					String.format("%s를 DB에 Null로 저장할 수 없습니다.", enumClass.getSimpleName()));

		return (attribute == null) ? null : attribute.getCode();
	}

	@Override
	public E convertToEntityAttribute(V dbData) {

		if (!nullable && dbData == null) 
			throw new IllegalArgumentException(
					String.format("%s가 DB에 Null로 저장되어 있습니다.", enumClass.getSimpleName()));

		if (dbData == null) return null;
		
		return EnumSet.allOf(enumClass).stream()
				.filter(x -> x.getCode().equals(dbData)).findAny()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("%s에 정의되지 않은 값입니다..", enumClass.getSimpleName())));
	}

}