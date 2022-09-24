package likelion.festival.service;


import likelion.festival.dto.CommentPasswordDto;
import likelion.festival.dto.CommentRequestDto;
import likelion.festival.dto.CommentResponseDto;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Comment;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongCommentId;
import likelion.festival.exception.WrongPassword;
import likelion.festival.repository.BoothRepository;
import likelion.festival.repository.CommentRepository;
import likelion.festival.security.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoothRepository boothRepository;
    private final Encrypt encrypt;


    public List<CommentResponseDto> getAll(Long boothId) {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        Booth booth = byId.get();
        List<Comment> comments = booth.getComments();
        return getDtoList(comments);
    }

    @Transactional
    public CommentResponseDto create(Long boothId, CommentRequestDto commentRequestDto) {
        Optional<Booth> byId = boothRepository.findById(boothId);

        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        Booth booth = byId.get();
        commentRequestDto.setBooth(booth);
        Comment comment = dtoToEntity(commentRequestDto);
        Comment save = commentRepository.save(comment);
        return entityToDto(save);
    }

    @Transactional
    public String delete(Long commentId, CommentPasswordDto password) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            throw new WrongCommentId();
        }
        Comment comment = byId.get();
        if (!comment.getPassword().equals(getEncPwd(password.getPassword()))) {
            throw new WrongPassword();
        }
        commentRepository.deleteById(commentId);
        return "Ok";
    }

    @Transactional
    public String force_delete(Long commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            throw new WrongCommentId();
        }
        commentRepository.deleteById(commentId);
        return "Ok";
    }


    public Comment dtoToEntity(CommentRequestDto commentRequestDto) {
        String enc_pwd = getEncPwd(commentRequestDto.getPassword());

        return Comment.builder()
                .writer(commentRequestDto.getWriter())
                .password(enc_pwd)
                .content(commentRequestDto.getContent())
                .booth(commentRequestDto.getBooth())
                .build();
    }


    private String getEncPwd(String password) {
        return this.encrypt.getEncrypt(password);
    }

    public CommentResponseDto entityToDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .createdDateTime(comment.getCreatedDateTime())
                .build();
    }


    private List<CommentResponseDto> getDtoList(List<Comment> all) {
        return all.stream().map(comment -> entityToDto(comment))
                .collect(Collectors.toList());
    }

}
