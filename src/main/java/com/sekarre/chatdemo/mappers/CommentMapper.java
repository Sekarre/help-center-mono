package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.comment.CommentResponseDTO;
import com.sekarre.chatdemo.domain.Comment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class CommentMapper {

    public abstract CommentResponseDTO mapCommentToCommentDTO(Comment comment);
    public abstract Comment mapCommentResponseDTOToComment(CommentResponseDTO commentResponseDTO);
    public abstract Comment mapCommentCreateRequestDTOToComment(CommentCreateRequestDTO commentCreateRequestDTO);
}
