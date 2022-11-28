package edu.jong.board.member.type;

import edu.jong.board.type.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements CodeEnum<Character> {
	
	MAIL('M'),
	FEMAIL('F');

	private final Character code;

}
