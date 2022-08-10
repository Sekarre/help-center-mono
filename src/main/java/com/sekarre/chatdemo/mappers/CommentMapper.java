package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.CommentDTO;
import com.sekarre.chatdemo.domain.Comment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class CommentMapper {

    public abstract CommentDTO mapCommentToCommentDTO(Comment comment);
    public abstract Comment mapCommentDTOToComment(CommentDTO commentDTO);
}
