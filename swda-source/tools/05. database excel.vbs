Sub 数据库设计整理规范宏脚本()

    ' 去掉网格线
    For i = 1 To Sheets.Count
        Set Sheet = Sheets(i)
        Sheet.Select
        Windows(1).DisplayGridlines = False
    Next
    
    ' 设置全局字体样式
    For i = 1 To Sheets.Count
        Set Sheet = Sheets(i)
        With Sheet.Cells.Font
            .Name = "宋体"
            .Size = 9
            .Color = RGB(66, 66, 66)
        End With
        With Sheet.Cells.Font
            .Name = "Calibri"
            .Size = 9
            .Color = RGB(66, 66, 66)
        End With
    Next

    ' 设置边框样式，行高及自适应列宽
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        Set Datas = Sheet.Range("A1:" + Chr(ColumnCount + 64) + CStr(RowCount))
        Datas.Borders(xlDiagonalDown).LineStyle = xlNone
        Datas.Borders(xlDiagonalUp).LineStyle = xlNone
        
        With Datas.Borders(xlEdgeLeft)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeTop)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeBottom)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeRight)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlInsideVertical)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlInsideHorizontal)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        
        Datas.Columns.AutoFit
        Datas.RowHeight = 16
        
    Next

    ' 设置表头变色
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        If Sheet.Name = "0. 表" Or Sheet.Name = "0. 字段" Then
        
            Set Datas = Sheet.Range("A1:" + Chr(ColumnCount + 64) + "1")
            
            With Datas.Font
                .ThemeColor = xlThemeColorDark1
                .TintAndShade = 0
            End With
            With Datas.Interior
                .Pattern = xlSolid
                .PatternColorIndex = xlAutomatic
                .Color = 15773696
                .TintAndShade = 0
                .PatternTintAndShade = 0
            End With
            Datas.Font.Bold = True
            With Datas
                .HorizontalAlignment = xlCenter
                .VerticalAlignment = xlCenter
                .WrapText = False
                .Orientation = 0
                .AddIndent = False
                .IndentLevel = 0
                .ShrinkToFit = False
                .ReadingOrder = xlContext
                .MergeCells = False
            End With
        End If
        
    Next

    ' 设置 表 跳转
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        If Sheet.Name = "0. 表" Then
            
            For j = 2 To RowCount
                Sheet.Hyperlinks.Add Anchor:=Sheet.Cells(j, 2), Address:="", SubAddress:= _
                "'" + Sheets(j + 2).Name + "'!A1"
                Sheet.Cells(j, 2).Font.Size = 9
            Next
           
        End If
        
    Next
    
    ' 设置 表格 样式
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        If Sheet.Name = "0. 表格" Then
            
            t = 1
            For j = 1 To RowCount
                If Sheet.Cells(j, 1).Text = "" Then
                    Set Datas = Sheet.Range("A" + CStr(j) + ":K" + CStr(j))
                    Datas.Borders(xlDiagonalDown).LineStyle = xlNone
                    Datas.Borders(xlDiagonalUp).LineStyle = xlNone
                    Datas.Borders(xlEdgeLeft).LineStyle = xlNone
                    With Datas.Borders(xlEdgeTop)
                        .LineStyle = xlContinuous
                        .ColorIndex = 0
                        .TintAndShade = 0
                        .Weight = xlThin
                    End With
                    With Datas.Borders(xlEdgeBottom)
                        .LineStyle = xlContinuous
                        .ColorIndex = 0
                        .TintAndShade = 0
                        .Weight = xlThin
                    End With
                    Datas.Borders(xlEdgeRight).LineStyle = xlNone
                    Datas.Borders(xlInsideVertical).LineStyle = xlNone
                    Datas.Borders(xlInsideHorizontal).LineStyle = xlNone
                End If
                If Sheet.Cells(j, 1).Text = "" Or t = 1 Then
                    If Sheet.Cells(j, 1).Text = "" Then
                        Set Datas = Sheet.Range("A" + CStr(j + 2) + ":K" + CStr(j + 2))
                    End If
                    If t = 1 Then
                        Set Datas = Sheet.Range("A" + CStr(j + 1) + ":K" + CStr(j + 1))
                    End If
                    'Set Datas = Sheet.Range("A" + CStr(j + 1) + ":K" + CStr(j + 1))
                    With Datas.Interior
                        .Pattern = xlSolid
                        .PatternColorIndex = xlAutomatic
                        .ThemeColor = xlThemeColorDark1
                        .TintAndShade = -0.149998474074526
                        .PatternTintAndShade = 0
                    End With
                    With Datas
                        .HorizontalAlignment = xlCenter
                        .VerticalAlignment = xlCenter
                        .WrapText = False
                        .Orientation = 0
                        .AddIndent = False
                        .IndentLevel = 0
                        .ShrinkToFit = False
                        .ReadingOrder = xlContext
                    End With
                    
                    If Sheet.Cells(j, 1).Text = "" Then
                        Set Datas = Sheet.Range("A" + CStr(j + 1) + ",C" + CStr(j + 1))
                    End If
                    If t = 1 Then
                        Set Datas = Sheet.Range("A" + CStr(j) + ",C" + CStr(j))
                    End If
                    With Datas.Font
                        .ThemeColor = xlThemeColorDark1
                        .TintAndShade = 0
                    End With
                    With Datas.Interior
                        .Pattern = xlSolid
                        .PatternColorIndex = xlAutomatic
                        .Color = 15773696
                        .TintAndShade = 0
                        .PatternTintAndShade = 0
                    End With
                    Datas.Font.Bold = True
                    With Datas
                        .HorizontalAlignment = xlCenter
                        .VerticalAlignment = xlCenter
                        .WrapText = False
                        .Orientation = 0
                        .AddIndent = False
                        .IndentLevel = 0
                        .ShrinkToFit = False
                        .ReadingOrder = xlContext
                        .MergeCells = False
                    End With
                    
                    If Sheet.Cells(j, 1).Text = "" Then
                        Set Datas = Sheet.Range("D" + CStr(j + 1) + ":K" + CStr(j + 1))
                    End If
                    If t = 1 Then
                        Set Datas = Sheet.Range("D" + CStr(j) + ":K" + CStr(j))
                    End If
                    With Datas
                        .HorizontalAlignment = xlCenter
                        .VerticalAlignment = xlCenter
                        .WrapText = False
                        .Orientation = 0
                        .AddIndent = False
                        .IndentLevel = 0
                        .ShrinkToFit = False
                        .ReadingOrder = xlContext
                        .MergeCells = False
                    End With
                    Datas.Merge
                    With Datas
                        .HorizontalAlignment = xlLeft
                        .VerticalAlignment = xlCenter
                        .WrapText = False
                        .Orientation = 0
                        .AddIndent = False
                        .IndentLevel = 0
                        .ShrinkToFit = False
                        .ReadingOrder = xlContext
                        .MergeCells = True
                    End With
                    
                    t = t + 1
                End If
    
            Next
           
        End If
        
    Next
    
    ' 设置 其他 样式
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        
        If Sheet.Name <> "0. 表" And Sheet.Name <> "0. 字段" And Sheet.Name <> "0. 表格" Then
            
            Set Datas = Sheet.Range("A2:K2")
            With Datas.Interior
                .Pattern = xlSolid
                .PatternColorIndex = xlAutomatic
                .ThemeColor = xlThemeColorDark1
                .TintAndShade = -0.149998474074526
                .PatternTintAndShade = 0
            End With
            With Datas
                .HorizontalAlignment = xlCenter
                .VerticalAlignment = xlCenter
                .WrapText = False
                .Orientation = 0
                .AddIndent = False
                .IndentLevel = 0
                .ShrinkToFit = False
                .ReadingOrder = xlContext
            End With
                    
                    
            Set Datas = Sheet.Range("A1,C1")
            With Datas.Font
                .ThemeColor = xlThemeColorDark1
                .TintAndShade = 0
            End With
            With Datas.Interior
                .Pattern = xlSolid
                .PatternColorIndex = xlAutomatic
                .Color = 15773696
                .TintAndShade = 0
                .PatternTintAndShade = 0
            End With
            Datas.Font.Bold = True
            With Datas
                .HorizontalAlignment = xlCenter
                .VerticalAlignment = xlCenter
                .WrapText = False
                .Orientation = 0
                .AddIndent = False
                .IndentLevel = 0
                .ShrinkToFit = False
                .ReadingOrder = xlContext
                .MergeCells = False
            End With
                    
            Set Datas = Sheet.Range("D1:K1")
            With Datas
                .HorizontalAlignment = xlCenter
                .VerticalAlignment = xlCenter
                .WrapText = False
                .Orientation = 0
                .AddIndent = False
                .IndentLevel = 0
                .ShrinkToFit = False
                .ReadingOrder = xlContext
                .MergeCells = False
            End With
            Datas.Merge
            With Datas
                .HorizontalAlignment = xlLeft
                .VerticalAlignment = xlCenter
                .WrapText = False
                .Orientation = 0
                .AddIndent = False
                .IndentLevel = 0
                .ShrinkToFit = False
                .ReadingOrder = xlContext
                .MergeCells = True
            End With
            
            Sheet.Hyperlinks.Add Anchor:=Sheet.Range("B1"), Address:="", SubAddress:= _
                "'" + Sheets(1).Name + "'!A" + CStr(i - 2)
            Sheet.Range("B1").Font.Size = 9
           
        End If
        
    Next
    
End Sub