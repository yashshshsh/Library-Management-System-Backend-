package com.fredo.LMS.software.service.interfac;

import com.fredo.LMS.software.dto.Response;

public interface IBorrowService {
    Response newBorrowing(Long bookId);

    Response returnBook(Long bookId, Long userId);

    Response renewBorrowing(Long borrowId);
}
